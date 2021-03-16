package com.platform.house.services;

import com.platform.house.constant.*;
import com.platform.house.domain.*;
import com.platform.house.dto.*;
import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseSearchForm;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import com.platform.house.utils.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-06-26 22:45
 */
@Service
@Transactional(readOnly = true)
public class HouseInfoQuery {

    @Autowired
    private HouseInfoRepo houseInfoRepo;

    @Autowired
    private HouseTypeRepo houseTypeRepo;

    @Autowired
    private HouseImageRepo houseImageRepo;

    @Autowired
    private AreaDivisionService areaDivisionService;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private HouseUserRepo houseUserRepo;

    @Autowired
    private HouseInfoNewsRepo houseInfoNewsRepo;

    @Autowired
    private MycenterHouseRepo mycenterHouseRepo;

    public PageVo<HouseDetailVo> search(HouseSearchForm searchForm, int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if (page > 0) {
            page = page - 1;
        }

        List<Long> filterHouseByType = null;
        if (searchForm.getRoomCount() != null || PriceType.P_ZJ.toString().equals(searchForm.getPriceType())) {
            List<HouseType> matchHouseType = houseTypeRepo.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if(searchForm.getRoomCount() != null) {
                	if (searchForm.getRoomCount() >= 5) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("roomCount"), searchForm.getRoomCount()));
                    } else {
                        predicates.add(cb.equal(root.get("roomCount"), searchForm.getRoomCount()));
                    }
                }
                // search by total price
                if(PriceType.P_ZJ.toString().equals(searchForm.getPriceType())) {
                	Integer priceBegin = searchForm.getPriceBegin();
                    Integer priceEnd = searchForm.getPriceEnd();
                    if(priceBegin != null && priceEnd == null) {
                    	predicates.add(cb.greaterThanOrEqualTo(root.get("totalPrice"), priceBegin));
                    }  else if(priceBegin == null && priceEnd != null) {
                    	predicates.add(cb.lessThanOrEqualTo(root.get("totalPrice"), priceEnd));
                    } else if(priceBegin != null && priceEnd != null) {
                    	predicates.add(cb.between(root.get("totalPrice"), priceBegin, priceEnd));
                    }   
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            });

            filterHouseByType = matchHouseType.stream().map(HouseType::getHouseId)
                    .distinct()
                    .collect(Collectors.toList());
        }

        PageRequest pageRequest = new PageRequest(page, size, sort);

        final List<Long> finalFilterHouseByType = filterHouseByType;
        Page<House> housePage = houseInfoRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.<Boolean>get("disabled"), Boolean.FALSE));
            String keywords = StringUtils.trimToEmpty(searchForm.getKeywords());
            if (StringUtils.isNotBlank(keywords)) {
                predicates.add(cb.or(
            		cb.like(root.get("name").as(String.class), "%"+keywords+"%"),
            		cb.like(root.get("address").as(String.class), "%"+keywords+"%"),
            		cb.like(root.get("introduction").as(String.class), "%"+keywords+"%")
                ));
            }
            // 房产状态
            if (StringUtils.isNotBlank(searchForm.getStatus())) {
                if (HouseInfoStatus.INIT.toString().equals(searchForm.getStatus())) {
                    predicates.add(cb.equal(root.get("status"), HouseInfoStatus.INIT));
                }
            }
            // 抓取网站
            if (StringUtils.isNotBlank(searchForm.getGrabSite())) {
                if ("ALL".equals(searchForm.getGrabSite())) {
                    predicates.add(cb.isNotNull(root.get("grabSite")));
                } else {
                    predicates.add(cb.equal(root.get("grabSite"), searchForm.getGrabSite()));
                }
            } else {
                if (searchForm.getHouseIdList() != null) {
                    predicates.add(root.get("id").in(searchForm.getHouseIdList()));
                }
            }
            if (StringUtils.isNotBlank(searchForm.getCity())) {
                predicates.add(cb.equal(root.get("city"), searchForm.getCity()));
            }
            
            if (StringUtils.isNotBlank(searchForm.getArea())) {
                predicates.add(cb.equal(root.get("area"), searchForm.getArea()));
            }
            
            if (StringUtils.isNotBlank(searchForm.getStreet())) {
                predicates.add(cb.equal(root.get("street"), searchForm.getStreet()));
            }
            // search by price
            if(PriceType.P_JJ.toString().equals(searchForm.getPriceType())) {
            	Integer priceBegin = searchForm.getPriceBegin();
                Integer priceEnd = searchForm.getPriceEnd();
                if(priceBegin != null && priceEnd == null) {
                	predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceBegin));
                }  else if(priceBegin == null && priceEnd != null) {
                	predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceEnd));
                } else if(priceBegin != null && priceEnd != null) {
                	predicates.add(cb.between(root.get("price"), priceBegin, priceEnd));
                }   
            }
               

            if (finalFilterHouseByType != null && !finalFilterHouseByType.isEmpty()) {
                CriteriaBuilder.In<Long> houseIdIn = cb.in(root.get("id"));
                for (Long aLong : finalFilterHouseByType) {
                    houseIdIn.value(aLong);
                }

                predicates.add(houseIdIn);
            } else if(finalFilterHouseByType != null && finalFilterHouseByType.isEmpty()) {
                predicates.add(cb.equal(root.get("id"), 0L));
            }


            predicates.add(cb.equal(root.get("disabled"), 0));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);

        if (!housePage.hasContent()) {
            return new PageVo<>(pageRequest);
        }


        List<HouseDetailVo> detailVOS = housePage.getContent().stream().map(this::mappingToListVo).collect(Collectors.toList());

        return new PageVo<HouseDetailVo>(detailVOS, pageRequest, housePage.getTotalElements());
    }
    
    public PageVo<HouseDetailVo> getFeaturedHouse(HouseSearchForm searchForm, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = new PageRequest(0, size, sort);
        Page<House> housePage = houseInfoRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.<Boolean>get("disabled"), Boolean.FALSE));
            predicates.add(cb.equal(root.<Boolean>get("isFeatured"), Boolean.TRUE));
            if (StringUtils.isNotBlank(searchForm.getCity())) {
                predicates.add(cb.equal(root.get("city"), searchForm.getCity()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);

        if (!housePage.hasContent()) {
            return new PageVo<>(pageRequest);
        }

        List<HouseDetailVo> detailVOS = housePage.getContent().stream().map(this::mappingToListVo).collect(Collectors.toList());

        return new PageVo<HouseDetailVo>(detailVOS, pageRequest, housePage.getTotalElements());
    }

    public House findById(Long houseId) {
        return houseInfoRepo.findOne(houseId);
    }

    public List<House> findByName(final String name) {
        House house = new House();
        house.setName(name);
        Example<House> example = Example.of(house);

        return houseInfoRepo.findAll(example);
    }

    // 抓取房源时如果本市存在相同的楼盘则认为是重复的
    public List<House> findByHouseForm(HouseInfoForm form) {
        House house = new House();
        house.setName(form.getName());
        house.setCity(form.getCity());
        // house.setGrabSite(form.getGrabSite());
        Example<House> example = Example.of(house);
        return houseInfoRepo.findAll(example);
    }

    public HouseDetailVo getDetails(Long houseId) {
        House houseInfo = houseInfoRepo.findOne(houseId);

        return mappingToDetailsVo(houseInfo);
    }

    private HouseDetailVo mappingToDetailsVo(House item) {
        return mappingToDetailsVo(item, false);
    }

    private HouseDetailVo mappingToListVo(House item) {
        return mappingToDetailsVo(item, true);
    }

    private HouseDetailVo mappingToDetailsVo(House item, Boolean isMapToList) {
        Assert.notNull(item, "房产信息不存在");

        HouseDetailVo vo = new HouseDetailVo();
        BeanUtils.copyProperties(item, vo);

        List<HouseType> types = houseTypeRepo.findAllByHouseId(item.getId());

        List<HouseTypeVo> houseTypeVos = types.stream().map(HouseTypeVo::new).sorted(Comparator.comparing(HouseTypeVo::getId).reversed()).collect(Collectors.toList());
        vo.setHouseTypes(houseTypeVos);
        List<HouseImageVo> houseImageVos = null;
        if (isMapToList) {
            Pageable pageable = new PageRequest(1, 1, Sort.Direction.DESC, "sort");
            Page<HouseImage> houseImagePage = houseImageRepo.findNormalImagesByHouseIdAndPage(item.getId(), pageable);
            List<HouseImage> images = houseImagePage.getContent();
            houseImageVos = images.stream().map(HouseImageVo::new).collect(Collectors.toList());
        } else {
            List<HouseImage> images = houseImageRepo.findNormalImagesByHouseId(item.getId());
            houseImageVos = images.stream().map(HouseImageVo::new)
                    .sorted(Comparator.comparing(HouseImageVo::getSort).reversed()).collect(Collectors.toList());
            List<HouseNews> houseNewsList = houseInfoNewsRepo.findHouseNewsByHouseId(item.getId());
            List<HouseNewsDto> houseNewsDtoList = houseNewsList.stream().map(HouseNewsDto::new).sorted(Comparator.comparing(HouseNewsDto::getId).reversed()).collect(Collectors.toList());
            vo.setHouseNewsList(houseNewsDtoList);

            List<HouseUser> houseUserList = houseUserRepo.findHouseUsersByHouseId(item.getId());
            List<UserDto> houseUserDtoList = houseUserList.stream().map(HouseUser::getUser).map(UserDto::new).collect(Collectors.toList());
            vo.setAgentList(houseUserDtoList);
        }

        vo.setNormalImages(houseImageVos);

        return vo;
    }

    public List<Long> getHouseIdList() {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        Boolean isAdmin = currentUser.hasRole(SysConstants.ROLE_ADMIN);
        Boolean isStoreAdmin = currentUser.hasRole(SysConstants.ROLE_STORE_ADMIN);
        // Boolean isAgent = currentUser.hasRole(SysConstants.ROLE_AGENT);
        List<Long> houseIdList = new ArrayList<Long>();
        List createIdList = new ArrayList();
        if (isAdmin) {
            return null;
        } else if (isStoreAdmin) {
            List<Staff> staffList = staffRepo.findByUserId(user.getId());
            if (staffList != null && !staffList.isEmpty()) {
                Store store = staffList.get(0).getStore();
                List<Staff> staffsInStore = staffRepo.findByStoreId(store.getId());
                createIdList = staffsInStore.stream().map(Staff::getUser).map(User::getId).distinct().collect(Collectors.toList());
            } else {
                createIdList.add(user.getId());
            }
        } else {
            createIdList.add(user.getId());
        }
        final List<Long> userIdList = createIdList;
        List<HouseUser> houseUserList = null;
        if (userIdList != null) {
            houseUserList = houseUserRepo.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(root.get("user").in(userIdList));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            });
            houseIdList = houseUserList.stream().map(HouseUser::getHouseId).distinct().collect(Collectors.toList());
            if (houseIdList.isEmpty()) {
                houseIdList.add(0L);
            }
        }
        return houseIdList;
    }

    public List<Long> getHouseIdListByWechatUser(Long userId, String displayType) {
        if (userId == null || StringUtils.isBlank(displayType)) {
            return new ArrayList<Long>() {{
                add(0L);
            }};
        }
        MycenterHouse mycenterHouse = new MycenterHouse();
        mycenterHouse.setUserId(userId);
        mycenterHouse.setDisplayType(DisplayType.valueOf(displayType));
        Example<MycenterHouse> example = Example.of(mycenterHouse);
        List<MycenterHouse> mycenterHouseList = mycenterHouseRepo.findAll(example);
        List<Long> houseIdList = mycenterHouseList.stream().map(MycenterHouse::getHouseId).distinct().collect(Collectors.toList());
        return houseIdList;
    }

}

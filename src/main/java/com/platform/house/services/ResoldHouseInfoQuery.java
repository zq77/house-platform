package com.platform.house.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.platform.house.constant.DisplayType;
import com.platform.house.constant.HouseCategory;
import com.platform.house.constant.HouseInfoStatus;
import com.platform.house.constant.SysConstants;
import com.platform.house.domain.*;
import com.platform.house.dto.BaseImageVo;
import com.platform.house.dto.LocationInfoVo;
import com.platform.house.dto.ResoldHouseVo;
import com.platform.house.form.HouseSearchForm;
import com.platform.house.form.ResoldHouseInfoForm;
import com.platform.house.repo.*;
import com.platform.house.security.ShiroUser;
import com.platform.house.utils.PageVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
public class ResoldHouseInfoQuery {

    @Autowired
    private ResoldHouseRepo resoldHouseRepo;

    @Autowired
    private AreaDivisionService areaDivisionService;

    @Autowired
    private ResoldHouseImageRepo resoldHouseImageRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ResoldHouseUserRepo resoldHouseUserRepo;

    @Autowired
    private MycenterHouseResoldRepo mycenterHouseResoldRepo;

    public PageVo<ResoldHouseVo> search(HouseSearchForm searchForm, int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        if (page > 0) {
            page = page - 1;
        }
        PageRequest pageRequest = new PageRequest(page, size, sort);

        Page<ResoldHouse> housePage = resoldHouseRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.<Boolean>get("disabled"), Boolean.FALSE));
            String keywords = StringUtils.trimToEmpty(searchForm.getKeywords());
            if (StringUtils.isNotBlank(keywords)) {
                predicates.add(cb.or(
                        cb.like(root.get("name").as(String.class), "%" + keywords + "%"),
                        cb.like(root.get("address").as(String.class), "%" + keywords + "%"),
                        cb.like(root.get("introduction").as(String.class), "%" + keywords + "%")
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
            Integer priceBegin = searchForm.getPriceBegin();
            Integer priceEnd = searchForm.getPriceEnd();
            if (priceBegin != null && priceEnd == null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("totalPrice"), priceBegin));
            } else if (priceBegin == null && priceEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalPrice"), priceEnd));
            } else if (priceBegin != null && priceEnd != null) {
                predicates.add(cb.between(root.get("totalPrice"), priceBegin, priceEnd));
            }

            if (searchForm.getRoomCount() != null) {
                if (searchForm.getRoomCount() >= 5) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("roomCount"), searchForm.getRoomCount()));
                } else {
                    predicates.add(cb.equal(root.get("roomCount"), searchForm.getRoomCount()));
                }
            }

            // search by area
            Double areaBegin = searchForm.getAreaBegin();
            Double areaEnd = searchForm.getAreaEnd();
            if (areaBegin != null && areaEnd == null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("floorage"), areaBegin));
            } else if (areaBegin == null && areaEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("floorage"), areaEnd));
            } else if (areaBegin != null && areaEnd != null) {
                predicates.add(cb.between(root.get("floorage"), areaBegin, areaEnd));
            }

            // search by category
            if (StringUtils.isNotBlank(searchForm.getCategory())) {
                predicates.add(cb.equal(root.get("category"), HouseCategory.valueOf(searchForm.getCategory())));
            }

            // search by decoration
            if (StringUtils.isNotBlank(searchForm.getDecoration())) {
                predicates.add(cb.equal(root.get("decoration"), searchForm.getDecoration()));
            }
            predicates.add(cb.equal(root.get("disabled"), 0));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageRequest);

        if (!housePage.hasContent()) {
            return new PageVo<>(pageRequest);
        }

        List<ResoldHouseVo> detailVOS = housePage.getContent().stream().map(this::mappingToDetailsVo).collect(Collectors.toList());

        return new PageVo<>(detailVOS, pageRequest, housePage.getTotalElements());
    }

    public PageVo<ResoldHouseVo> getFeaturedHouse(HouseSearchForm searchForm, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequest pageRequest = new PageRequest(0, size, sort);
        Page<ResoldHouse> housePage = resoldHouseRepo.findAll((root, query, cb) -> {
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

        List<ResoldHouseVo> detailVOS = housePage.getContent().stream().map(this::mappingToDetailsVo).collect(Collectors.toList());

        return new PageVo<>(detailVOS, pageRequest, housePage.getTotalElements());
    }

    public ResoldHouseVo getDetails(Long houseId) {
        ResoldHouse houseInfo = resoldHouseRepo.findOne(houseId);

        return mappingToDetailsVo(houseInfo);
    }

    private ResoldHouseVo mappingToDetailsVo(ResoldHouse item) {
        Assert.notNull(item, "房产信息不存在");
        ResoldHouseVo houseVo = new ResoldHouseVo();
        BeanUtils.copyProperties(item, houseVo);

        appendAddressInfo(houseVo);

        List<ResoldHouseImage> images = resoldHouseImageRepo.findByResoldHouseId(item.getId());
        List<BaseImageVo> imageVos = images.stream().map(image -> new BaseImageVo(image.getId(), image.getImageUrl())).sorted(Comparator.comparing(BaseImageVo::getId).reversed()).collect(Collectors.toList());
        houseVo.setImages(imageVos);

        return houseVo;
    }

    public List<ResoldHouse> findByName(String name) {
        ResoldHouse house = new ResoldHouse();
        house.setName(name);
        Example<ResoldHouse> example = Example.of(house);

        return resoldHouseRepo.findAll(example);

    }

    public List<ResoldHouse> findByHouseForm(ResoldHouseInfoForm form) {
        ResoldHouse house = new ResoldHouse();
        house.setTitle(form.getTitle());
        house.setCity(form.getCity());
        house.setGrabSite(form.getGrabSite());
        Example<ResoldHouse> example = Example.of(house);
        return resoldHouseRepo.findAll(example);
    }

    public ResoldHouse findById(Long houseId) {
        return resoldHouseRepo.findOne(houseId);
    }


    private void appendAddressInfo(LocationInfoVo info) {
        info.setProvinceStr(getByCode(areaDivisionService.getProvinces(), info.getProvince()));
        info.setCityStr(getByCode(areaDivisionService.getCities(), info.getCity()));
        info.setAreaStr(getByCode(areaDivisionService.getAreas(), info.getArea()));
        info.setStreetStr(getByCode(areaDivisionService.getStreets(), info.getStreet()));
    }

    private String getByCode(JSONArray array, String code) {
        if (Strings.isNullOrEmpty(code)) {
            return "";
        }
        return array.stream().map(item -> (JSONObject) item)
                .filter((item) -> code.equals(item.get("code")))
                .map(item -> item.get("name").toString()).findFirst().orElse("");
    }

    public List<Long> getResoldHouseIdList() {
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
        List<ResoldHouseUser> houseUserList = null;
        if (userIdList != null) {
            houseUserList = resoldHouseUserRepo.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(root.get("userId").in(userIdList));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            });
            houseIdList = houseUserList.stream().map(ResoldHouseUser::getResoldHouseId).distinct().collect(Collectors.toList());
            if (houseIdList.isEmpty()) {
                houseIdList.add(0L);
            }
        }
        return houseIdList;
    }

    public List<Long> getResoldHouseIdListByWechatUser(Long userId, String displayType) {
        if (userId == null || StringUtils.isBlank(displayType)) {
            return new ArrayList<Long>() {{
                add(0L);
            }};
        }
        MycenterHouseResold mycenterHouse = new MycenterHouseResold();
        mycenterHouse.setUserId(userId);
        mycenterHouse.setDisplayType(DisplayType.valueOf(displayType));
        Example<MycenterHouseResold> example = Example.of(mycenterHouse);
        List<MycenterHouseResold> mycenterHouseList = mycenterHouseResoldRepo.findAll(example);
        List<Long> houseIdList = mycenterHouseList.stream().map(MycenterHouseResold::getHouseId).distinct().collect(Collectors.toList());
        return houseIdList;
    }
}

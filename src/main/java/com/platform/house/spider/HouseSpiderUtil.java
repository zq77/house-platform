package com.platform.house.spider;

import com.alibaba.fastjson.JSONObject;
import com.platform.house.constant.*;
import com.platform.house.form.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HouseSpiderUtil {

    private static final Logger logger = Logger.getLogger(HouseSpiderUtil.class);

    public static void process(HouseSpiderProcessor houseProcessor, Page page) {
        HouseSpiderForm houseSpiderForm = houseProcessor.getHouseSpiderForm();
        if (page.getUrl().regex(houseSpiderForm.getListUrlReg()).match()) {
            grabListPage(page, houseSpiderForm);

        } else if (page.getUrl().regex(houseSpiderForm.getDetailUrlReg()).match()) {
            grabDetailPage(page, houseSpiderForm);
        } else {
            page.setSkip(true);
        }
    }

    public static void processResoldHouse(HouseSpiderProcessor houseProcessor, Page page) {
        HouseSpiderForm houseSpiderForm = houseProcessor.getHouseSpiderForm();
        if (page.getUrl().regex(houseSpiderForm.getListUrlReg()).match()) {
            grabListPageForResoldHouse(page, houseSpiderForm);

        } else if (page.getUrl().regex(houseSpiderForm.getDetailUrlReg()).match()) {
            grabDetailPageForResoldHouse(page, houseSpiderForm);

        } else {
            page.setSkip(true);
        }
    }



    private static void grabListPage(Page page, HouseSpiderForm houseSpiderForm) {

        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        switch (website) {
            case LJ:
                HouseSpiderUtilForLJ.grabListPage(page, houseSpiderForm);
                break;
            case BK:
                HouseSpiderUtilForBK.grabListPage(page, houseSpiderForm);
                break;
            case AJK:
                HouseSpiderUtilForAJK.grabListPage(page, houseSpiderForm);
                break;
            case YYHF:
                HouseSpiderUtilForYYHF.grabListPage(page, houseSpiderForm);
                break;
            default:
                HouseSpiderUtilForLJ.grabListPage(page, houseSpiderForm);
                break;
        }
    }

    public static String NEW_HOUSE_DETAIL_KEY = "newHouseDetail";
    public static String NEW_HOUSE_TYPE_KEY = "newHouseType";
    public static String NEW_HOUSE_IMAGE_KEY = "newHouseImage";

    private static void grabDetailPage(Page page, HouseSpiderForm houseSpiderForm) {
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        JSONObject selectedCity = houseSpiderForm.getSelectedCity();
        String grabPageUrl = page.getUrl().toString();
        String detailPartUrl = website.getNewHouseDetailPartUrl();
        String typePartUrl = website.getNewHouseTypePartUrl();
        String imagePartUrl = website.getNewHouseImagePartUrl();
        if (grabPageUrl.indexOf(detailPartUrl) >= 0) {
            HouseInfoForm houseForm = grabHouseInfoFromHtml(page, houseSpiderForm);
            if (StringUtils.isBlank(houseForm.getName())) {
                page.setSkip(true);
                return;
            }
            houseForm.setCity(selectedCity.getString("code"));
            houseForm.setProvince(selectedCity.getString("parent_code"));
            houseForm.setStatus(HouseInfoStatus.GRAB.toString());
            houseForm.setGrabSite(website.toString());
            String basePageUrl = grabPageUrl.replace(detailPartUrl, "");
            Map<String, HouseInfoForm> houseInfoMap = new ConcurrentHashMap<>();
            houseInfoMap.put(basePageUrl, houseForm);
            page.putField(NEW_HOUSE_DETAIL_KEY, houseInfoMap);
        } else if (grabPageUrl.indexOf(typePartUrl) >= 0) {
            List<HouseTypeForm> houseTypeFormList = grabHouseTypeFromHtml(page, website);
            String basePageUrl = grabPageUrl.replace(typePartUrl, "");
            Map<String, List<HouseTypeForm>> houseTypeMap = new ConcurrentHashMap<>();
            houseTypeMap.put(basePageUrl, houseTypeFormList);
            page.putField(NEW_HOUSE_TYPE_KEY, houseTypeMap);
        } else if (grabPageUrl.indexOf(imagePartUrl) >= 0) {
            List<NormalImageFrom> houseImageList = grabHouseImageFromHtml(page, website);
            String basePageUrl = grabPageUrl.replace(imagePartUrl, "");
            Map<String, List<NormalImageFrom>> houseImageMap = new ConcurrentHashMap<>();
            houseImageMap.put(basePageUrl, houseImageList);
            page.putField(NEW_HOUSE_IMAGE_KEY, houseImageMap);

        } else {
            page.setSkip(true);
            return;
        }

    }

    private static HouseInfoForm grabHouseInfoFromHtml(Page page, HouseSpiderForm houseSpiderForm) {
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        Html html = page.getHtml();
        HouseInfoForm houseForm;
        switch (website) {
            case LJ:
                houseForm = HouseSpiderUtilForLJ.grabHouseInfoFromHtml(html, houseSpiderForm);
                break;
            case BK:
                houseForm = HouseSpiderUtilForBK.grabHouseInfoFromHtml(html, houseSpiderForm);
                break;
            case AJK:
                houseForm = HouseSpiderUtilForAJK.grabHouseInfoFromHtml(html);
                break;
            case YYHF:
                houseForm = HouseSpiderUtilForYYHF.grabHouseInfoFromHtml(html);
                break;
            default:
                houseForm = HouseSpiderUtilForLJ.grabHouseInfoFromHtml(html, houseSpiderForm);
                break;
        }
        return houseForm;
    }

    private static List<HouseTypeForm> grabHouseTypeFromHtml(Page page, HouseWebsite website) {
        Html html = page.getHtml();
        List<HouseTypeForm> houseTypeFormList;
        switch (website) {
            case LJ:
                houseTypeFormList = HouseSpiderUtilForLJ.grabHouseTypeFromHtml(html);
                break;
            case BK:
                houseTypeFormList = HouseSpiderUtilForBK.grabHouseTypeFromHtml(html);
                break;
            default:
                houseTypeFormList = HouseSpiderUtilForLJ.grabHouseTypeFromHtml(html);
                break;
        }
        return houseTypeFormList;
    }

    private static List<NormalImageFrom> grabHouseImageFromHtml(Page page, HouseWebsite website) {
        Html html = page.getHtml();
        List<NormalImageFrom> houseImageList;
        switch (website) {
            case LJ:
                houseImageList = HouseSpiderUtilForLJ.grabHouseImageFromHtml(html);
                break;
            case BK:
                houseImageList = HouseSpiderUtilForBK.grabHouseImageFromHtml(html);
                break;
            default:
                houseImageList = HouseSpiderUtilForLJ.grabHouseImageFromHtml(html);
                break;
        }
        return houseImageList;
    }

    private static void grabListPageForResoldHouse(Page page, HouseSpiderForm houseSpiderForm) {
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        switch (website) {
            case LJ:
                HouseSpiderUtilForLJ.grabListPageForResold(page, houseSpiderForm);
                break;
            case BK:
                HouseSpiderUtilForBK.grabListPageForResold(page, houseSpiderForm);
                break;
            case AJK:
                HouseSpiderUtilForAJK.grabListPageForResold(page, houseSpiderForm);
                break;
            case YYHF:
                HouseSpiderUtilForYYHF.grabListPageForResold(page, houseSpiderForm);
                break;
            default:
                HouseSpiderUtilForLJ.grabListPageForResold(page, houseSpiderForm);
                break;
        }
    }

    private static void grabDetailPageForResoldHouse(Page page, HouseSpiderForm houseSpiderForm) {
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        JSONObject selectedCity = houseSpiderForm.getSelectedCity();
        ResoldHouseInfoForm houseForm = grabHouseInfoFromHtmlForResoldHouse(page, houseSpiderForm);
        if (StringUtils.isBlank(houseForm.getTitle())) {
            page.setSkip(true);
            return;
        }
        houseForm.setCity(selectedCity.getString("code"));
        houseForm.setProvince(selectedCity.getString("parent_code"));
        houseForm.setStatus(HouseInfoStatus.GRAB.toString());
        houseForm.setGrabSite(website.toString());
        page.putField("resoldHouse", houseForm);
    }

    private static ResoldHouseInfoForm grabHouseInfoFromHtmlForResoldHouse(Page page, HouseSpiderForm houseSpiderForm) {
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        Html html = page.getHtml();
        ResoldHouseInfoForm houseForm;
        switch (website) {
            case LJ:
                houseForm = HouseSpiderUtilForLJ.grabHouseInfoFromHtmlForResold(html, houseSpiderForm);
                break;
            case BK:
                houseForm = HouseSpiderUtilForBK.grabHouseInfoFromHtmlForResold(html, houseSpiderForm);
                break;
            case AJK:
                houseForm = HouseSpiderUtilForAJK.grabHouseInfoFromHtmlForResold(html);
                break;
            case YYHF:
                houseForm = HouseSpiderUtilForYYHF.grabHouseInfoFromHtmlForResold(html);
                break;
            default:
                houseForm = HouseSpiderUtilForLJ.grabHouseInfoFromHtmlForResold(html, houseSpiderForm);
                break;
        }
        return houseForm;
    }

    private static final List<SpiderReflectObject> reflectObjList = new ArrayList<SpiderReflectObject>() {
        {
            add(new SpiderReflectObject(new String[]{"????????????", "????????????"}, "setPrice", Double.class));
            add(new SpiderReflectObject(new String[]{"????????????", "????????????"}, "setAddress", String.class));
            add(new SpiderReflectObject(new String[]{"????????????", "???????????????", "???????????????"}, "setSalesDepartmentAddress", String.class));
            add(new SpiderReflectObject(new String[]{"?????????"}, "setDevelopers", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setPropertyCompany", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setBuildYear", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setCompletionDate", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setSellingDate", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setCategory", String.class));
            add(new SpiderReflectObject(new String[]{"??????", "?????????", "????????????"}, "setParkCount", String.class));
            add(new SpiderReflectObject(new String[]{"?????????"}, "setGreeningRate", String.class));
            add(new SpiderReflectObject(new String[]{"?????????"}, "setPlotRatio", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setPropertyRightsYears", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setHouseholds", String.class));
            add(new SpiderReflectObject(new String[]{"????????????", "?????????"}, "setPropertyPrice", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setTotalArea", Double.class));
            add(new SpiderReflectObject(new String[]{"????????????", "???????????????"}, "setFloorage", Double.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setIntroduction", String.class));
        }
    };

    private static final List<SpiderReflectObject> reflectObjListForResoldHouse = new ArrayList<SpiderReflectObject>() {
        {
            add(new SpiderReflectObject(new String[]{"????????????", "??????"}, "setName", String.class));
            add(new SpiderReflectObject(new String[]{"??????"}, "setAddress", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setPrice", Double.class));
            add(new SpiderReflectObject(new String[]{"???"}, "setRoomCount", Integer.class));
            add(new SpiderReflectObject(new String[]{"???"}, "setHallCount", Integer.class));
            add(new SpiderReflectObject(new String[]{"???"}, "setKitchenCount", Integer.class));
            add(new SpiderReflectObject(new String[]{"???"}, "setBathroomCount", Integer.class));
            add(new SpiderReflectObject(new String[]{"??????", "????????????"}, "setFloor", String.class));
            add(new SpiderReflectObject(new String[]{"?????????"}, "setTotalFloor", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setFloorage", Double.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setUseageArea", Double.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setOrientation", String.class));
            add(new SpiderReflectObject(new String[]{"????????????", "????????????"}, "setDecoration", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setUseYear", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setPropertyYear", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setPropertyNature", String.class));
            add(new SpiderReflectObject(new String[]{"????????????", "????????????"}, "setCategory", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setBuildingYear", String.class));
            add(new SpiderReflectObject(new String[]{"????????????"}, "setMortgageStatus", String.class));
        }
    };

    private static final Map<String, HouseCategory> categroyMap = new ConcurrentHashMap<String, HouseCategory>() {
        {
            put("??????", HouseCategory.NORMAL);
            put("????????????", HouseCategory.NORMAL);
            put("??????", HouseCategory.VILLA);
            put("??????", HouseCategory.SHOP);
            put("??????", HouseCategory.SHOP);
            put("??????", HouseCategory.SHOP);
            put("?????????", HouseCategory.OFFICE);
            put("???????????????", HouseCategory.OFFICE);
        }
    };

    public static final Map<String, SellingStatus> sellingStatusMap = new ConcurrentHashMap<String, SellingStatus>() {
        {
            put("??????", SellingStatus.FOR_SELL);
            put("??????", SellingStatus.WILL_SELL);
            put("??????", SellingStatus.SOLD_OUT);
        }
    };

    public static final Map<String, ImageType> imageTypeMap = new ConcurrentHashMap<String, ImageType>() {
        {
            put("?????????", ImageType.XGT);
            put("?????????", ImageType.SJT);
            put("??????", ImageType.QW);
            put("????????????", ImageType.XQPT);
            put("????????????", ImageType.XMXC);
            put("???????????????", ImageType.YSXKZ);
        }
    };

    private static final List<String> noDataList = new ArrayList<String>() {
        {
            add("??????");
            add("????????????");
            add("????????????");
            add("????????????");
            add("????????????");
            add("-");
        }
    };

    public static void setNewHouseInfo(HouseInfoForm houseForm, String label, String labelValue) {
        setHouseInfo(houseForm, label, labelValue, reflectObjList);
    }

    public static void setResoldHouseInfo(ResoldHouseInfoForm houseForm, String label, String labelValue) {
        setHouseInfo(houseForm, label, labelValue, reflectObjListForResoldHouse);
    }

    public static Pattern DOUBLE_PATTERN = Pattern.compile("(\\d+\\.?\\d*)");

    private static void setHouseInfo(Object houseForm, String label, String labelValue, List<SpiderReflectObject> reflectObjList) {
        for (SpiderReflectObject reflectObject : reflectObjList) {
            if (StringUtils.isNotBlank(label) && Arrays.asList(reflectObject.getLabel()).contains(label.replaceAll("(:|???)", ""))) {
                if (noDataList.contains(labelValue) || StringUtils.isBlank(labelValue)) {
                    break;
                }
                labelValue = labelValue.trim();
                Method method = getMethodByName(houseForm.getClass(), reflectObject.getMethod(), reflectObject.getParamType());
                try {
                    if (reflectObject.getParamType().equals(Double.class)) {
                        Matcher matcher = DOUBLE_PATTERN.matcher(labelValue.replaceAll(",", ""));
                        if (matcher.find()) {
                            String value = matcher.group();
                            Double doubleValue = Double.parseDouble(value);
                            method.invoke(houseForm, doubleValue);
                        }
                    } else if (reflectObject.getParamType().equals(Integer.class)) {
                        Integer intValue = Integer.parseInt(labelValue.replace("???", ""));
                        method.invoke(houseForm, intValue);
                    } else {
                        if ("setCategory".equals(reflectObject.getMethod())) {
                            if (categroyMap.containsKey(labelValue)) {
                                method.invoke(houseForm, categroyMap.get(labelValue).toString());
                            } else {
                                method.invoke(houseForm, HouseCategory.OTHERS.toString());
                            }
                        } else {
                            method.invoke(houseForm, labelValue);
                        }
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    logger.error("----------------------------------?????????---------------------------------");
                    logger.error(reflectObject.getLabel() + "---" + labelValue + "---" + reflectObject.getMethod() + "---" + reflectObject.getParamType());
                    logger.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error("----------------------------------?????????---------------------------------");
                    logger.error(reflectObject.getLabel() + "---" + labelValue + "---" + reflectObject.getMethod() + "---" + reflectObject.getParamType());
                    logger.error(e.getMessage());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    logger.error("----------------------------------?????????---------------------------------");
                    logger.error(reflectObject.getLabel() + "---" + labelValue + "---" + reflectObject.getMethod() + "---" + reflectObject.getParamType());
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private static Map<String, Method> methodMap = new ConcurrentHashMap<>();

    private static Method getMethodByName(Class clz, String methodName, Class paramType) {
        Method method = methodMap.get(methodName + clz.toString());
        if (method != null) {
            return method;
        } else {
            try {
                method = clz.getMethod(methodName, paramType);
                methodMap.put(methodName + clz.toString(), method);
            } catch (NoSuchMethodException e) {
                logger.error("----------------------------------?????????---------------------------------");
                logger.error(methodName);
                logger.error(e.getMessage());
            }
        }
        return method;
    }

    public static Map<String, String> getNumberForHouseType(String houseTypeStr) {
        String[] types = {"???", "???", "???", "???"};
        Map<String, String> houseTypes = new ConcurrentHashMap<>();
        StringBuffer houseTypeBuffer = new StringBuffer(houseTypeStr);
        for (String type : types) {
            Integer currentCharPosition = houseTypeBuffer.indexOf(type);
            if (houseTypeBuffer.indexOf(type) >= 0) {
                houseTypes.put(type, houseTypeBuffer.substring(0, currentCharPosition));
                houseTypeBuffer = new StringBuffer(houseTypeBuffer.substring(currentCharPosition + 1));
            } else {
                houseTypes.put(type, "");
            }
        }
        return houseTypes;
    }

    public static void main(String[] args) {

        Pattern p = Pattern.compile("(\\d+\\.?\\d*)");
        // Matcher m=p.matcher("140,000.52???".replaceAll(",", ""));
        // Matcher m=p.matcher("?????? 5700???/???".replaceAll(",", ""));
        // System.out.println(m.find());
        // System.out.println(m.group());
//        while (m.find()) {
//            System.out.println(m.group());
//        }
        String labelValue = "19318 ???/m?";
        Matcher matcher = p.matcher(labelValue.replaceAll(",", ""));
        // String labelValueNum = labelValue.replaceAll(FILTER_STRING, "");
        if (matcher.find()) {
            String value = matcher.group();
            Double doubleValue = Double.parseDouble(value);
            System.out.println(value);
            System.out.println(doubleValue);
        }
    }

}

package com.platform.house.domain;

import com.platform.house.constant.Education;
import com.platform.house.constant.MaritalStatus;
import com.platform.house.constant.PoliticalStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // 工号
    @Column(name = "job_number")
    private String jobNumber;

    // 入职日期
    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "birthday")
    private Date birthday;

    // 加入方式（招聘渠道）
    @Column(name = "join_way")
    private String joinWay;

    // 推荐人
    @Column(name = "refferer")
    private String referrer;

    // 籍贯
    @Column(name = "birthplace")
    private String birthplace;

    // 民族
    @Column(name = "nationality")
    private String nationality;

    // 政治面貌
    @Column(name = "political")
    @Enumerated(EnumType.STRING)
    private PoliticalStatus political;

    // 婚姻情况
    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    // 最高学历
    @Column(name = "education")
    @Enumerated(EnumType.STRING)
    private Education education;

    // 毕业年份
    @Column(name = "graduated_year")
    private String graduatedYear;

    // 毕业学校
    @Column(name = "graduated_school")
    private String graduatedSchool;

    // 专业
    @Column(name = "specialty")
    private String specialty;

    // 紧急联系人
    @Column(name = "emergency_contact")
    private String emergencyContact;

    // 紧急联系电话
    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(name = "enabled")
    private Boolean enabled;

    public Staff() {
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getJoinWay() {
        return joinWay;
    }

    public void setJoinWay(String joinWay) {
        this.joinWay = joinWay;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public PoliticalStatus getPolitical() {
        return political;
    }

    public void setPolitical(PoliticalStatus political) {
        this.political = political;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getGraduatedYear() {
        return graduatedYear;
    }

    public void setGraduatedYear(String graduatedYear) {
        this.graduatedYear = graduatedYear;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

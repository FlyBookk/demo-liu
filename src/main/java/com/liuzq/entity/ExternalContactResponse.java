package com.liuzq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalContactResponse {

    private int errcode;
    private String errmsg;
    private List<ExternalContactInfo> external_contact_list;
    private String next_cursor;


    // Inner static class for FollowInfo
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowInfo {
        private String userid;
        private String remark;
        private String description;
        private long createtime;
        private List<Integer> tagId;
        private List<String> remarkMobiles;
        private int addWay;
        private String operUserid;

    }

    // Inner static class for ExternalContact
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExternalContact {
        private String externalUserid;
        private String name;
        private int type;
        private String avatar;
        private int gender;

    }

    // Inner static class for holding both FollowInfo and ExternalContact
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExternalContactInfo {
        private FollowInfo followInfo;
        private ExternalContact externalContact;


    }
}
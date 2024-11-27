package com.liuzq.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatExternalContactResponse {

    @JsonProperty("errcode")
    private int errcode;

    @JsonProperty("errmsg")
    private String errmsg;

    @JsonProperty("external_contact_list")
    private List<ExternalContactInfo> externalContactList;

    @JsonProperty("next_cursor")
    private String nextCursor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExternalContactInfo {

        @JsonProperty("follow_info")
        private FollowInfo followInfo;

        @JsonProperty("external_contact")
        private ExternalContact externalContact;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class FollowInfo {

            @JsonProperty("userid")
            private String userid;

            @JsonProperty("remark")
            private String remark;

            @JsonProperty("description")
            private String description;

            @JsonProperty("createtime")
            private long createtime;

            @JsonProperty("tag_id")
            private List<String> tagId; // Assuming tag_id is an array of integers

            @JsonProperty("remark_mobiles")
            private List<String> remarkMobiles; // Assuming remark_mobiles is an array of strings  

            @JsonProperty("add_way")
            private int addWay;

            @JsonProperty("oper_userid")
            private String operUserid;

        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ExternalContact {

            @JsonProperty("external_userid")
            private String externalUserid;

            @JsonProperty("name")
            private String name;

            @JsonProperty("type")
            private int type;

            @JsonProperty("avatar")
            private String avatar;

            @JsonProperty("gender")
            private int gender;

            @JsonProperty("unionid")
            private String unionid;
        }
    }
}
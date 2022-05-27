package org.whh.bz.enums;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public enum UploadStatus {
    ALL_UP(2,"全部上传完成"),
    PART_UP(1,"部分重复文件"),
    NULL(0,"输入为空"),
    ALL_Repeat(-1,"全部重复");

    private int code;
    private String msg;

    UploadStatus(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }
}

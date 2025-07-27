package com.cyh.mall.common.constant;

public interface ResponseConstant {

    interface Save{

        String SUCCESS = "保存成功";

        String FAIL = "保存失败";

        interface Category{

            String CATEGORY_EXIST = "种类已存在";

            String PARENT_CATEGORY_NOT_EXIST = "父类型不存在";
        }
    }

    interface Update{
        String SUCCESS = "更新成功";

        String FAIL = "更新失败";

        String FAIL_PARTITION="部分修改失败";
    }

    interface Delete{
        String SUCCESS = "删除成功";

        String FAIL = "删除失败";

        String NULL_LIST="删除列表为空";

        String NOT_DELETE="有子类目与之关联无法删除";
    }
}

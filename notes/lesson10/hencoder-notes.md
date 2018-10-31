#### 布局

#### 流程

1. root view 递归调用子view的measure方法
2. root view 递归调用子view的layout方法


两次测量
1. 父view的宽度是wrap_content
2. 子view的宽度是match_parent

第一次测量确认父view的宽度，将子view的宽度设定成0
第二次测量已经得到父view的宽度，子view的宽度设定为match_parent = 父view的宽度


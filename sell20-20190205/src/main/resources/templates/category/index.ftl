<html>
    <#include "../common/header.ftl">
<body>
<#--最外层 -->
<div id="wrapper" class="toggled">

<#--侧边栏 -->
    <#include "../common/nav.ftl">

<#--主要内容 -->
    <div id="page-content-wrapper">
    <#--右边数据部分 -->
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <#--为提交操作加上地址-->
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>类目名</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>类目type</label>
                            <input name="categoryType" type="text" class="form-control" value="${(category.categoryType)!""}"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!""}">
                        <button type="submit" class="btn-default">提交</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>
<#--<#list orderDTOPage.content as orderDTO>
    ${orderDTO.orderId}<br>
</#list>-->

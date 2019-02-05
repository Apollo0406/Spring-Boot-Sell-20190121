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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img height="100" width="100" src="${(productInfo.productIcon)!""}" alt="">
                            <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#--注意要让选择框选中当前商品的类目，需要做判断 -->
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <#--加一个隐藏的字段-->
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!""}">
                        <div class="checkbox">
                            <label><input type="checkbox" />Check me out</label>
                        </div><button type="submit" class="btn btn-default">提交</button>
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

package org.youyou.main;

import org.youyou.utils.JdbcUtil;
import org.youyou.utils.MoveUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author youpengfeiinfo
 * @version 13-6-16
 */
public class MoveData {
    public static void main(String[] args) throws IOException, SQLException {
        MoveUtils.moveData("authority.csv", "srm_sys_authority", "1", "id,displayName,name,parentId,nodeType,url,operator,yn,createTime,updateTime,authorityArea");
        MoveUtils.moveData("product_sort.csv", "srm_sys_role_productsort", "1", "id,roleId,productSortThirdId,productSortThirdName,createTime,modifyTime,yn");
        MoveUtils.moveData("brand.csv", "srm_sys_role_merchandise", "1", "[id],[roleId],[brandName],[yn]");
        MoveUtils.moveData("purchaser.csv", "srm_sys_role_purchaser", "1", "id,roleId,purchaser,purchaserPin,createTime,modifyTime,yn,purchaserid");
        MoveUtils.moveData("user_register.csv", "srm_sys_user_register", "1", "id,userpin,providerCode,providerName,createTime,updateTime,yn,userId");
        MoveUtils.moveData("role.csv", "srm_sys_role", "1", "id,name,operator,updateTime,createTime,yn,providerId");
        MoveUtils.moveData("user_role.csv", "srm_sys_user_role", "1", "id,userId,roleId,yn,createTime,updateTime");
        MoveUtils.moveData("user.csv", "srm_sys_user", "1", "id,loginName,name,plainPassword,providerId,userType,shaPassword,reveal,yn,createTime,updateTime,userpin");
        MoveUtils.moveData("provider_data.csv", "srm_provider", "1", "id,providerName,providerErpId,providerCode,createTime,updateTime,yn,OWN_SIGN,providerAccount,STS,DRES,GODS");
    }
}

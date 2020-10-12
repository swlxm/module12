package com.epam.automation;

import com.epam.automation.common.GlobalVar;
import com.epam.automation.common.runner.BaseRunner;
import com.epam.automation.common.utils.JsonUtils;
import com.epam.automation.common.utils.ProxyUtils;
import com.epam.automation.common.utils.TimeUtils;
import io.restassured.http.Header;
import io.restassured.mapper.TypeRef;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SmokeCheck extends BaseRunner {

    private String newId = "";
    private String month = TimeUtils.getLocalFormatMonth(0);
    private SmokeCheckApi smokeCheckApi = ProxyUtils.create(SmokeCheckApi.class);


    @BeforeTest
    public void before() {
    }

//    /**
//     * 创建交易（带活动和礼品）
//     */
//    @Test
//    public void postTransactions() {
//        String json = JsonUtils.POST_TRANSACTION;
//        json = JsonUtils.setjsonData(json, "memberId", "122000000369");
//        response = transactionApi.postTransactions(json);
//        response.then().statusCode(201);
//        List<Map<String, Object>> list = response.body().jsonPath().getList("products");
//        Map<String, Object> item = list.get(0);
//        assertThat(item.get("displayName"), equalTo(GlobalVar.PRODUCT_NAME_77));
//        response.prettyPrint();
//        response.then().body("status", equalTo("SUBMITTED"));
//        newId = response.getBody().jsonPath().get("id").toString();
//        logger.info("create new transaction id is " + newId);
//    }
//
//    /**
//     * 查询交易
//     */
    @Test
    public void testStatusCode() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(items.size(), greaterThan(0));
        response.prettyPrint();
    }

    @Test
    public void testBody() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(items.size(), equalTo(10));
        response.prettyPrint();
    }

    @Test
    public void testHeader() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        assertThat(response.headers().hasHeaderWithName("Content-Type"), equalTo(true));
        String contentType = response.header("Content-Type");
        assertThat(contentType, equalTo("application/json; charset=utf-8"));
    }
//
//    /**
//     * 查询会员历史交易
//     */
//    @Test
//    public void queryMemberTransactionHistory() {
//        String json = "{\n" +
//            "  \"beginPurchaseDate\": \"" + TimeUtils.getLocalFormatDay(-30) + "\",\n" +
//            "  \"endPurchaseDate\": \"" + TimeUtils.getLocalFormatDay(0) + "\",\n" +
//            "  \"isWeb\": 0,\n" +
//            "  \"memberId\": \"" + GlobalVar.MEMBER_ID + "\",\n" +
//            "  \"searchString\": \"" + GlobalVar.BAR_CODE_77 + "\"\n" +
//            "}";
//        response = transactionApi.queryMemberTransactions(json, "", "");
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//    }
//
//    /**
//     * 获取活动列表
//     */
//    @Test
//    public void getEligibleCampaigns() {
//        response = promotionApi.getEligibleCampaigns("", GlobalVar.STORE_ID, TimeUtils.getNowInstant().toString());
//        response.prettyPrint();
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        List<String> campaigns = new ArrayList<>();
//        for(Map<String, Object> item:items) {
//            campaigns.add(item.get("sourceCampaignId").toString());
//        }
//        logger.info("The eligible campaigns are " + campaigns);
//    }
//
//    /**
//     * 获取活动列表
//     */
//    @Test
//    public void getEligibleCampaigns2() {
//        response = promotionApi.getEligibleCampaigns(GlobalVar.MEMBER_ID, GlobalVar.STORE_ID, TimeUtils.getNowInstant().toString());
//        response.prettyPrint();
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        List<String> campaigns = new ArrayList<>();
//        for(Map<String, Object> item:items) {
//            campaigns.add(item.get("sourceCampaignId").toString());
//        }
//        logger.info("The eligible campaigns are " + campaigns);
//    }
//
//    /**
//     * 价格验证
//     */
//    @Test
//    public void validatePrice() {
//        String json = "{\n" +
//            "  \"products\": [\n" +
//            "    {\n" +
//            "      \"price\": " + GlobalVar.RETAIL_PRICE_78 + ",\n" +
//            "      \"productCode\": \"" + GlobalVar.PRODUCT_CODE_78 + "\"\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"purchaseDate\": \"" + TimeUtils.getNowInstant() + "\",\n" +
//            "  \"storeId\": \"" + GlobalVar.STORE_ID + "\"\n" +
//            "}";
//        response = productApi.validatePrice(json);
//        response.prettyPrint();
//        response.then().statusCode(200);
//        response.then().body("status", equalTo(200));
//    }
//
//    private int getProductQTY(String storeId, String barCode) {
//        response = storeWarehouseApi.queryWarehouseStorage(storeId, barCode, "", "", "", "", "MERCHANDISE");
//        response.prettyPrint();
//        return Integer.valueOf(response.body().jsonPath().getList("productInfos.qty").get(0).toString());
//    }
//
//    /**
//     * store_warehouse里的store_tier字段必须是0
//     * @throws InterruptedException
//     */
//    @Test
//    public void checkInventory_PostTransaction() throws InterruptedException {
//        //check original qty
//        int productQtyBefore = getProductQTY(GlobalVar.STORE_ID, GlobalVar.BAR_CODE_84);
//        logger.info("the original qty is {}", productQtyBefore);
//        //post new transaction
//        String json = JsonUtils.POST_TRANSACTION;
//        json = JsonUtils.setjsonData(json, "storeId", GlobalVar.STORE_ID);
//        json = JsonUtils.setJsonArrayData(json, "products", 0, "productCode", GlobalVar.PRODUCT_CODE_84);
//        json = JsonUtils.setJsonArrayData(json, "products", 0, "barCode", GlobalVar.BAR_CODE_84);
//        json = JsonUtils.setJsonArrayData(json, "products", 0, "price", GlobalVar.PRICE_84);
//        json = JsonUtils.setJsonArrayData(json, "products", 0, "retailPrice", GlobalVar.RETAIL_PRICE_84);
//        json = JsonUtils.setJsonArrayData(json, "products", 0, "displayName", GlobalVar.PRODUCT_NAME_84);
//        response = transactionApi.postTransactions(json);
//        //sleep 10s and get qty again
//        logger.info("sleeping {} seconds start.......", GlobalVar.SLEEP_LONG/1000);
//        Thread.sleep(GlobalVar.SLEEP_LONG);
//        int productQtyAfter = getProductQTY(GlobalVar.STORE_ID, GlobalVar.BAR_CODE_84);
//        logger.info("the latest qty is {}", productQtyAfter);
//        //the qty should subtract 1
//        assertThat(productQtyBefore - productQtyAfter, equalTo(2));
//    }
//
//    /**
//     * 查询库存
//     */
//    @Test
//    public void queryWarehouse() {
//        response = storeWarehouseApi.queryWarehouseStorage(GlobalVar.STORE_ID, GlobalVar.BAR_CODE_77,
//            "", "", "", "", "MERCHANDISE");
//        response.then().assertThat().statusCode(200);
//        response.then().body("productInfos.productBarCode", everyItem(equalTo(GlobalVar.BAR_CODE_77)));
//    }
//
//    /**
//     * 查询会员
//     */
//    @Test
//    public void SearchCustomerListByPhone() {
//        response = personaApi.searchCustomerListByCondition("", "", "", "13915507802", "");
//        response.then().assertThat().statusCode(200);
//        response.prettyPrint();
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), equalTo(1));
//        assertThat(items.get(0).get("memberName"), equalTo("Sam"));
//    }
//
//    /**
//     * TS交易确认
//     * 数据来源：report微服务的transaction_seller_summary表, where store_id='A-SH-SH-021-CT178', 因为9870e4a98fcb4d98869c2a5955b591b4是store owner
//     */
//    @Test
//    public void getTransDataByTS() throws InterruptedException {
//        GlobalVar.HEADERS.replace("Authorization", GlobalVar.token_ts);
//        response = transactionConfirmApi.getTransDataByTS("olay", "cn", GlobalVar.TS, month, "E");
//        response.then().statusCode(200);
//        response.prettyPrint();
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.get(0).get("storeId"), equalTo(GlobalVar.STORE_ID));
//        float realAmount = Float.valueOf(items.get(0).get("realAmount").toString());
//        int sellerCheckQty = Integer.valueOf(items.get(0).get("sellerCheckQty").toString());
//        int transQty = Integer.valueOf(items.get(0).get("transQty").toString());
//        int transProdQty = Integer.valueOf(items.get(0).get("transProdQty").toString());
//        assertThat(sellerCheckQty, equalTo(transQty));
//        GlobalVar.HEADERS.replace("Authorization", GlobalVar.token_bc);
//        response = transactionApi.postTransactions(JsonUtils.POST_TRANSACTION);
//        response.prettyPrint();
//        response.then().statusCode(201);
//        Thread.sleep(GlobalVar.SLEEP_LONG);
//        GlobalVar.HEADERS.replace("Authorization", GlobalVar.token_ts);
//        response = transactionConfirmApi.getTransDataByTS("olay", "cn", GlobalVar.TS, month, "E");
//        response.then().statusCode(200);
//        response.prettyPrint();
//        items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        float realAmount_new = Float.valueOf(items.get(0).get("realAmount").toString());
//        int sellerCheckQty_new = Integer.valueOf(items.get(0).get("sellerCheckQty").toString());
//        int transQty_new = Integer.valueOf(items.get(0).get("transQty").toString());
//        int transProdQty_new = Integer.valueOf(items.get(0).get("transProdQty").toString());
//        assertThat(sellerCheckQty_new, equalTo(transQty_new));
//        assertThat(sellerCheckQty_new-sellerCheckQty, equalTo(1));
//        assertThat(transQty_new-transQty, equalTo(1));
//        assertThat(transProdQty_new-transProdQty, equalTo(2));
//        assertThat(realAmount_new-realAmount, equalTo(GlobalVar.PRICE_77.floatValue()*2));
//    }
//
//    /**
//     * 先抽盘，然后报表里查询并获取id，最后盘点审核
//     * @throws InterruptedException
//     */
//    @Test
//    public void auditInventory() throws InterruptedException {
//        com.pg.cn.integration.reporting.InventoryCheckApi reportApi = ProxyUtils.create(com.pg.cn.integration.reporting.InventoryCheckApi.class);
//        String postJson_checkInventory = "";
//        String id = null;
//        String json = JsonUtils.setJsonArrayData(postJson_checkInventory, "items", 0, "productBarCode", "6903148026076");
//        json = JsonUtils.setJsonArrayData(json, "items", 0, "productCode", "82255231（FMP）");
//        json = JsonUtils.setJsonArrayData(json, "items", 0, "productName", "玉兰油乳液透亮洁面乳100G（贴非卖品）");
//        response = inventoryCheckApi.postCheckInventory(json);
//        response.prettyPrint();
//        Thread.sleep(GlobalVar.SLEEP_LONG);
//        String queryAuditInventoriesJson = "{\n" +
//            "  \"productType\": \"TRIALSAMPLE\",\n" +
//            "  \"checkingStatus\": \"PENDINGAPPROVAL\",\n" +
//            "  \"checkingType\": \"PARTIALCHECK\",\n" +
//            "  \"startDate\": \"" + TimeUtils.getLocalFormatDay(0) + "\",\n" +
//            "  \"endDate\": \"" + TimeUtils.getLocalFormatDay(0) + "\"\n" +
////            "  \"mmId\": \"ZHENG-YY-1\"\n" +
//            "}";
//        GlobalVar.HEADERS.replace("Authorization", GlobalVar.token_admin);
//        response = reportApi.queryAuditInventories(queryAuditInventoriesJson);
//        response.then().assertThat().statusCode(200);
//        response.prettyPrint();
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        for(Map<String, Object> item:items) {
//            if(item.get("productBarCode").toString().equals("6903148026076")) {
//                id = item.get("id").toString();
//            }
//        }
//        String body = "[{\"storeId\":\"" + GlobalVar.STORE_ID + "\",\"id\":\"" + id + "\",\"comments\":null}]";
//        response = inventoryCheckApi.auditInventory("auditRole", "auditName", true, body);
//        response.then().statusCode(200);
//        response.then().body("result", equalTo("审核完成"));
//        response.then().body("status", equalTo("OK"));
//    }
//
//    /**
//     * 验证WISE
//     */
//    @Test
//    public void getDeliveryList() {
//        response = productReceivingApi.getDeliveryList("2019-03-01 00:00:00", "2019-03-30 00:00:00", "", GlobalVar.STORE_ID, "GIFT");
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//    }
//
//    /**
//     * 产品同步
//     */
//    @Test
//    public void getProductsBySync() {
//        //什么情况下会返回数据
//        response = productApi.getProductsBySync(GlobalVar.LAST_MODIFIED_DATE,"MERCHANDISE", 10000, 0);
//        response.then().assertThat().statusCode(200);
//        response.prettyPrint();
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        logger.info("{} products were found.", items.size());
//        for(Map<String, Object> item:items) {
//            if(item.get("productCode").toString().equals(GlobalVar.PRODUCT_CODE_77)) {
//                System.out.println(item.toString());
//                assertThat(true, equalTo(true));
//                break;
//            }
//        }
//    }
//
//    /**
//     * 实际价格同步
//     */
//    @Test
//    public void findSyncPriceState() {
//        response = productApi.findSyncPriceState(GlobalVar.STORE_ID, GlobalVar.LAST_MODIFIED_DATE, 0, 10);
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        logger.info("{} prices were found.", items.size());
//        for(Map<String, Object> item:items) {
//            if(item.get("productCode").toString().equals(GlobalVar.PRODUCT_CODE_77)) {
//                System.out.println(item.toString());
//                assertThat(item.get("price"), equalTo(GlobalVar.PRICE_77.floatValue()));
//                break;
//            }
//        }
//    }
//
//    /**
//     * 建议价格同步
//     */
//    @Test
//    public void findSyncPriceState2() {
//        response = productApi.findSyncPriceState(GlobalVar.LAST_MODIFIED_DATE, 0, 500);
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        logger.info("{} prices were found.", items.size());
//        for(Map<String, Object> item:items) {
//            if(item.get("productCode").toString().equals(GlobalVar.PRODUCT_CODE_77)) {
//                System.out.println(item.toString());
//
//                assertThat(Float.valueOf(item.get("price").toString()), equalTo(GlobalVar.RETAIL_PRICE_77.floatValue()));
//                break;
//            }
//        }
//    }
//
//    /**
//     * 活动同步，类型0和2
//     */
//    @Test
//    public void testCampaignMaster() {
//        response = promotionApi.campaignMaster(GlobalVar.LAST_MODIFIED_DATE, 0, 500);
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        response.prettyPrint();
//    }
//
//    @Test
//    public void testCampaignGift() {
//        response = promotionApi.campaignGift(GlobalVar.LAST_MODIFIED_DATE, 0, 500);
//        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), greaterThan(0));
//        response.prettyPrint();
//    }

}

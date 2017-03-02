package com.tubic.testapp.data.source;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FakeGoogleSearchRemoteDataSource implements GoogleSearchRemoteDataSource {


    @Override
    public Observable<HashMap> search(@Query("q") String query, @Query("start") Integer start, @Query("num") int limit, @Query("key") String key, @Query("cx") String cx, @Query("searchType") String searchType) {
        if (query.equals("empty")) {
            return Observable.just(new HashMap()).delay(1, TimeUnit.SECONDS);
        }

        return Observable.just(new Gson().fromJson(RESPONSE, HashMap.class)).delay(1, TimeUnit.SECONDS);
    }

    private static final String RESPONSE = "{\n" +
            " \"kind\": \"customsearch#search\",\n" +
            " \"url\": {\n" +
            "  \"type\": \"application/json\",\n" +
            "  \"template\": \"https://www.googleapis.com/customsearch/v1?q={searchTerms}&num={count?}&start={startIndex?}&lr={language?}&safe={safe?}&cx={cx?}&cref={cref?}&sort={sort?}&filter={filter?}&gl={gl?}&cr={cr?}&googlehost={googleHost?}&c2coff={disableCnTwTranslation?}&hq={hq?}&hl={hl?}&siteSearch={siteSearch?}&siteSearchFilter={siteSearchFilter?}&exactTerms={exactTerms?}&excludeTerms={excludeTerms?}&linkSite={linkSite?}&orTerms={orTerms?}&relatedSite={relatedSite?}&dateRestrict={dateRestrict?}&lowRange={lowRange?}&highRange={highRange?}&searchType={searchType}&fileType={fileType?}&rights={rights?}&imgSize={imgSize?}&imgType={imgType?}&imgColorType={imgColorType?}&imgDominantColor={imgDominantColor?}&alt=json\"\n" +
            " },\n" +
            " \"queries\": {\n" +
            "  \"request\": [\n" +
            "   {\n" +
            "    \"title\": \"Google Custom Search - tesla\",\n" +
            "    \"totalResults\": \"146000000\",\n" +
            "    \"searchTerms\": \"tesla\",\n" +
            "    \"count\": 10,\n" +
            "    \"startIndex\": 1,\n" +
            "    \"inputEncoding\": \"utf8\",\n" +
            "    \"outputEncoding\": \"utf8\",\n" +
            "    \"safe\": \"off\",\n" +
            "    \"cx\": \"001376575074506113973:mm_0bg2y_ow\",\n" +
            "    \"searchType\": \"image\"\n" +
            "   }\n" +
            "  ],\n" +
            "  \"nextPage\": [\n" +
            "   {\n" +
            "    \"title\": \"Google Custom Search - tesla\",\n" +
            "    \"totalResults\": \"146000000\",\n" +
            "    \"searchTerms\": \"tesla\",\n" +
            "    \"count\": 10,\n" +
            "    \"startIndex\": 11,\n" +
            "    \"inputEncoding\": \"utf8\",\n" +
            "    \"outputEncoding\": \"utf8\",\n" +
            "    \"safe\": \"off\",\n" +
            "    \"cx\": \"001376575074506113973:mm_0bg2y_ow\",\n" +
            "    \"searchType\": \"image\"\n" +
            "   }\n" +
            "  ]\n" +
            " },\n" +
            " \"context\": {\n" +
            "  \"title\": \"Test\"\n" +
            " },\n" +
            " \"searchInformation\": {\n" +
            "  \"searchTime\": 1.409751,\n" +
            "  \"formattedSearchTime\": \"1.41\",\n" +
            "  \"totalResults\": \"146000000\",\n" +
            "  \"formattedTotalResults\": \"146,000,000\"\n" +
            " },\n" +
            " \"items\": [\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla | Premium Electric Sedans and SUVs\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> | Premium Electric Sedans and SUVs\",\n" +
            "   \"link\": \"https://www.teslamotors.com/sites/default/files/red-tesla-model-s.jpg\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Tesla | Premium Electric ...\",\n" +
            "   \"htmlSnippet\": \"<b>Tesla</b> | Premium Electric ...\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/\",\n" +
            "    \"height\": 745,\n" +
            "    \"width\": 1400,\n" +
            "    \"byteSize\": 505328,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSE8Ee4m_fW3w7JvsJZkB0WdgXo76olirM1rD3oMBSsZWowXXsm1TKdTD0B\",\n" +
            "    \"thumbnailHeight\": 80,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Model S | Tesla\",\n" +
            "   \"htmlTitle\": \"Model S | <b>Tesla</b>\",\n" +
            "   \"link\": \"https://www.tesla.com/tesla_theme/assets/img/savings-calculator/models_white_wall_2x.jpg\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Road Trip\",\n" +
            "   \"htmlSnippet\": \"Road Trip\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/models\",\n" +
            "    \"height\": 680,\n" +
            "    \"width\": 1456,\n" +
            "    \"byteSize\": 136962,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRhea7w4Cw9tYKMT0Tjs5r4Uw2YO8CYpEO80QQqPedvQcAdr8xDZ6-osxs\",\n" +
            "    \"thumbnailHeight\": 70,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla Car Reviews - Tesla Pricing, Photos and Specs - CARandDRIVER\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> Car Reviews - <b>Tesla</b> Pricing, Photos and Specs - CARandDRIVER\",\n" +
            "   \"link\": \"http://buyersguide.caranddriver.com/media/assets/submodel/7651.jpg\",\n" +
            "   \"displayLink\": \"www.caranddriver.com\",\n" +
            "   \"snippet\": \"Model S\",\n" +
            "   \"htmlSnippet\": \"Model S\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"http://www.caranddriver.com/tesla\",\n" +
            "    \"height\": 489,\n" +
            "    \"width\": 800,\n" +
            "    \"byteSize\": 164492,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRStC7yi2yEjhY929aFLMn9V627Kj_zxIXytNzubGcZdiOpEFK3q7vAufA\",\n" +
            "    \"thumbnailHeight\": 87,\n" +
            "    \"thumbnailWidth\": 143\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla | Premium Electric Sedans and SUVs\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> | Premium Electric Sedans and SUVs\",\n" +
            "   \"link\": \"https://www.tesla.com/tesla_theme/assets/img/modals/model-select-modelx.png?20160811\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Model S Modal Model X Modal\",\n" +
            "   \"htmlSnippet\": \"Model S Modal Model X Modal\",\n" +
            "   \"mime\": \"image/png\",\n" +
            "   \"fileFormat\": \"Image Document\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/\",\n" +
            "    \"height\": 409,\n" +
            "    \"width\": 778,\n" +
            "    \"byteSize\": 63309,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQh5vh86K32WznWOZk7bErMCnGg9R8DMSBq4cJ7Q-Q8aQLIPVbuHRLoWxIm\",\n" +
            "    \"thumbnailHeight\": 75,\n" +
            "    \"thumbnailWidth\": 142\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla | Inhabitat - Green Design, Innovation, Architecture, Green ...\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> | Inhabitat - Green Design, Innovation, Architecture, Green ...\",\n" +
            "   \"link\": \"http://assets.inhabitat.com/wp-content/blogs.dir/1/files/2016/10/Tesla-Model-S.jpg\",\n" +
            "   \"displayLink\": \"inhabitat.com\",\n" +
            "   \"snippet\": \"Tesla | Inhabitat - Green ...\",\n" +
            "   \"htmlSnippet\": \"<b>Tesla</b> | Inhabitat - Green ...\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"http://inhabitat.com/tag/tesla/\",\n" +
            "    \"height\": 922,\n" +
            "    \"width\": 1580,\n" +
            "    \"byteSize\": 575151,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQ4B3r-WJNYZOSSYN-5pov2G5ZF-IAHlkhj5NwuvYom44aHX1ixtJVm-Yzs\",\n" +
            "    \"thumbnailHeight\": 88,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla | Premium Electric Sedans and SUVs\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> | Premium Electric Sedans and SUVs\",\n" +
            "   \"link\": \"https://www.tesla.com/tesla_theme/assets/img/modals/model-select-models.png?20160811\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Model S Modal ...\",\n" +
            "   \"htmlSnippet\": \"Model S Modal ...\",\n" +
            "   \"mime\": \"image/png\",\n" +
            "   \"fileFormat\": \"Image Document\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/\",\n" +
            "    \"height\": 365,\n" +
            "    \"width\": 781,\n" +
            "    \"byteSize\": 63516,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSAWZNzlvD81qmQgveZpLTeA5XOqLnVN5JQ_IqM1Cal8OSi35f2VIVuwW0u\",\n" +
            "    \"thumbnailHeight\": 67,\n" +
            "    \"thumbnailWidth\": 143\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Tesla News, Photos and Buying Information - Autoblog\",\n" +
            "   \"htmlTitle\": \"<b>Tesla</b> News, Photos and Buying Information - Autoblog\",\n" +
            "   \"link\": \"http://o.aolcdn.com/commerce/autodata/images/USC30TSC021B021001.jpg\",\n" +
            "   \"displayLink\": \"www.autoblog.com\",\n" +
            "   \"snippet\": \"2016 Tesla Model S\",\n" +
            "   \"htmlSnippet\": \"2016 <b>Tesla</b> Model S\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"http://www.autoblog.com/tesla/\",\n" +
            "    \"height\": 1386,\n" +
            "    \"width\": 2100,\n" +
            "    \"byteSize\": 235142,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQmBCtIWj_PG53DOSbLE2auk1cDOBJ_dxmMVxQG1Hr6YJR_j6XuTmI9MQG9\",\n" +
            "    \"thumbnailHeight\": 99,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Model X | Tesla\",\n" +
            "   \"htmlTitle\": \"Model X | <b>Tesla</b>\",\n" +
            "   \"link\": \"https://www.tesla.com/tesla_theme/assets/img/modelx/section-hero-background.jpg?20161201\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Model X\",\n" +
            "   \"htmlSnippet\": \"Model X\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/modelx\",\n" +
            "    \"height\": 625,\n" +
            "    \"width\": 1440,\n" +
            "    \"byteSize\": 130748,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQENoBSha4jRQqm6Tq7ZjwKVPEWP87dlFuVxJeAn3aNuLscec4JVEjYzEQ\",\n" +
            "    \"thumbnailHeight\": 65,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Meet Tesla's Model 3, Its Long-Awaited Car for the Masses | WIRED\",\n" +
            "   \"htmlTitle\": \"Meet <b>Tesla&#39;s</b> Model 3, Its Long-Awaited Car for the Masses | WIRED\",\n" +
            "   \"link\": \"https://assets.wired.com/photos/w_1749/wp-content/uploads/2016/03/model-3-unveil2.jpg\",\n" +
            "   \"displayLink\": \"www.wired.com\",\n" +
            "   \"snippet\": \"Caption: Tesla\",\n" +
            "   \"htmlSnippet\": \"Caption: <b>Tesla</b>\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.wired.com/2016/03/meet-teslas-model-3-long-awaited-car-masses/\",\n" +
            "    \"height\": 984,\n" +
            "    \"width\": 1749,\n" +
            "    \"byteSize\": 222992,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSrKWmG8HVDCf7NxL5iJ39gIhRx7TFrZ6rOA2bWaD0Skvwc5nYDxIfVuORK\",\n" +
            "    \"thumbnailHeight\": 84,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  },\n" +
            "  {\n" +
            "   \"kind\": \"customsearch#result\",\n" +
            "   \"title\": \"Model S | Tesla\",\n" +
            "   \"htmlTitle\": \"Model S | <b>Tesla</b>\",\n" +
            "   \"link\": \"https://www.tesla.com/tesla_theme/assets/img/savings-calculator/models_white_super_2x.jpg\",\n" +
            "   \"displayLink\": \"www.tesla.com\",\n" +
            "   \"snippet\": \"Charge time based on 90D ...\",\n" +
            "   \"htmlSnippet\": \"Charge time based on 90D ...\",\n" +
            "   \"mime\": \"image/jpeg\",\n" +
            "   \"image\": {\n" +
            "    \"contextLink\": \"https://www.tesla.com/models\",\n" +
            "    \"height\": 680,\n" +
            "    \"width\": 1456,\n" +
            "    \"byteSize\": 144044,\n" +
            "    \"thumbnailLink\": \"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToy5_rHs8FaDis_3V7OkyPFqpZWi21UH-tOXfEnTRrBEyPaFx20KYmMwMX\",\n" +
            "    \"thumbnailHeight\": 70,\n" +
            "    \"thumbnailWidth\": 150\n" +
            "   }\n" +
            "  }\n" +
            " ]\n" +
            "}";
}

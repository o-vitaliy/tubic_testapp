package com.tubic.testapp.data.source;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by ovitaliy on 27.02.2017.
 */

public class FakeFacebookRemoteDataSource extends FacebookRemoteDataSource {



    @Override
    public Observable<JSONObject> getImages(String after, int limit) {
        try {
            return Observable.just(new JSONObject(RESPONSE));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String RESPONSE = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"images\": [\n" +
            "        {\n" +
            "          \"height\": 720,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/381128_249703028417451_1422118212_n.jpg?oh=c75cb11a998370c721f59519e46fba70&oe=5933CDFD\",\n" +
            "          \"width\": 960\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 600,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p600x600/381128_249703028417451_1422118212_n.jpg?oh=35e79c02f5890de83d20d8525351af43&oe=59403728\",\n" +
            "          \"width\": 800\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 480,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p480x480/381128_249703028417451_1422118212_n.jpg?oh=0aeff55bea393ca924c107d4145d6309&oe=592610B9\",\n" +
            "          \"width\": 640\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 320,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p320x320/381128_249703028417451_1422118212_n.jpg?oh=c9cd8d727a73d5e64ef377146f49e8e8&oe=593DBB75\",\n" +
            "          \"width\": 426\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 540,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p180x540/381128_249703028417451_1422118212_n.jpg?oh=3657942c2b181d8167600e9ef33b536d&oe=5938A83E\",\n" +
            "          \"width\": 720\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 130,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p130x130/381128_249703028417451_1422118212_n.jpg?oh=db5e2ca2c968781e2acd03bff4b389ab&oe=596BAA34\",\n" +
            "          \"width\": 173\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 225,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p75x225/381128_249703028417451_1422118212_n.jpg?oh=84acc07a7a1416bf6f56ac9d5b3682b4&oe=5934D565\",\n" +
            "          \"width\": 300\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": \"249703028417451\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"images\": [\n" +
            "        {\n" +
            "          \"height\": 720,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/373857_249703001750787_1359788764_n.jpg?oh=df4aef0bc9d272004480ec2e049259d4&oe=593C89EA\",\n" +
            "          \"width\": 960\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 600,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p600x600/373857_249703001750787_1359788764_n.jpg?oh=ce12abaff87d385131876f09b8b48f63&oe=593CCD3F\",\n" +
            "          \"width\": 800\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 480,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p480x480/373857_249703001750787_1359788764_n.jpg?oh=1f65e9e424b1a094f4be7a793db799b8&oe=593C67AE\",\n" +
            "          \"width\": 640\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 320,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p320x320/373857_249703001750787_1359788764_n.jpg?oh=8a4de075d9e6e88f8cbee1dd57c873d2&oe=5939EF62\",\n" +
            "          \"width\": 426\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 540,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p180x540/373857_249703001750787_1359788764_n.jpg?oh=f9ea8ec4fbb95bbf4b540df3dd2fdcf3&oe=5973BD29\",\n" +
            "          \"width\": 720\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 130,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p130x130/373857_249703001750787_1359788764_n.jpg?oh=b8f222873127bc38ad783909f7503b8e&oe=59275623\",\n" +
            "          \"width\": 173\n" +
            "        },\n" +
            "        {\n" +
            "          \"height\": 225,\n" +
            "          \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-0/p75x225/373857_249703001750787_1359788764_n.jpg?oh=001c2d899c158a92d276b61e1dd21548&oe=593A0372\",\n" +
            "          \"width\": 300\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": \"249703001750787\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"paging\": {\n" +
            "    \"cursors\": {\n" +
            "      \"before\": \"MjQ5NzAzMDI4NDE3NDUx\",\n" +
            "      \"after\": \"MjQ5NzAzMDAxNzUwNzg3\"\n" +
            "    },\n" +
            "    \"next\": \"https://graph.facebook.com/v2.8/1242486345805776/photos?access_token=EAAF5YjWMIgEBAG81XtO80EZCTmPMQzv1N2rjHQ7mbWjjdhEYXDOLaWclJiCJ6OHohE4QgMl7UUkoHrp3SHbYQxTAKuMjkk06wCIpwiaG2aBA65cf4J6QZACc0EUo9JzL78HyWpnkRjHUxZAQM7Rv5SA7uIyvTKt4g2fJ3UWWCixeZChfD0pM7QlVZC16PV4wZD&pretty=0&fields=images&type=uploaded&limit=2&after=MjQ5NzAzMDAxNzUwNzg3\"\n" +
            "  }\n" +
            "}";
}

package tech.itpark.projectdelivery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectDeliveryApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void crudTest() throws Exception {
        mockMvc.perform(get("/orders/"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"delivererId\": 1,\n" +
                                        "    \"customerId\": 1,\n" +
                                        "    \"date\": \"2020-12-10T09:12:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 219,\n" +
                                        "    \"total\": 1300,\n" +
                                        "    \"status\": 0\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"delivererId\": 5,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"date\": \"2020-12-11T14:10:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      3,\n" +
                                        "      4\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 893,\n" +
                                        "    \"total\": 1600,\n" +
                                        "    \"status\": 0\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"delivererId\": 5,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"date\": \"2020-12-11T12:25:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      5\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 893,\n" +
                                        "    \"total\": 850,\n" +
                                        "    \"status\": 0\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"delivererId\": 4,\n" +
                                        "    \"customerId\": 3,\n" +
                                        "    \"date\": \"2020-12-12T18:45:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      7\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 342,\n" +
                                        "    \"total\": 980,\n" +
                                        "    \"status\": 0\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/orders/1"))
                .andExpect(
                        content()
                                .json("\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"delivererId\": 1,\n" +
                                        "    \"customerId\": 1,\n" +
                                        "    \"date\": \"2020-12-10T09:12:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 219,\n" +
                                        "    \"total\": 1300,\n" +
                                        "    \"status\": 0\n" +
                                        "  }")
                );

        mockMvc.perform(get("/orders/search")
                .queryParam("delivererId", String.valueOf(5))
                .queryParam("date1", "2020-12-10 00:00:01")
                .queryParam("date2", "2020-12-13 23:59:59"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"delivererId\": 5,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"date\": \"2020-12-11T14:10:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      3,\n" +
                                        "      4\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 893,\n" +
                                        "    \"total\": 1600,\n" +
                                        "    \"status\": 0\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"delivererId\": 5,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"date\": \"2020-12-11T12:25:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      5\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 893,\n" +
                                        "    \"total\": 850,\n" +
                                        "    \"status\": 0\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/orders/customers/1"))
                .andExpect(
                        content()
                                .json("[{\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"delivererId\": 1,\n" +
                                        "    \"customerId\": 1,\n" +
                                        "    \"date\": \"2020-12-10T09:12:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 219,\n" +
                                        "    \"total\": 1300,\n" +
                                        "    \"status\": 0\n" +
                                        "  }]")
                );

        mockMvc.perform(get("/orders/vendors/1"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"delivererId\": 1,\n" +
                                        "    \"customerId\": 1,\n" +
                                        "    \"date\": \"2020-12-10T09:12:14.395+00:00\",\n" +
                                        "    \"products\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"deliveryPrice\": 219,\n" +
                                        "    \"total\": 1300,\n" +
                                        "    \"status\": 0\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(delete("/orders/4/remove"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 4,\n" +
                                        "  \"delivererId\": 4,\n" +
                                        "  \"customerId\": 3,\n" +
                                        "  \"date\": \"2020-12-12T18:45:14.395+00:00\",\n" +
                                        "  \"products\": [\n" +
                                        "    7\n" +
                                        "  ],\n" +
                                        "  \"qty\": [\n" +
                                        "    2\n" +
                                        "  ],\n" +
                                        "  \"deliveryPrice\": 342,\n" +
                                        "  \"total\": 980,\n" +
                                        "  \"status\": 0\n" +
                                        "}")
                );

        mockMvc.perform(get("/carts"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"customerId\": 1,\n" +
                                        "    \"vendorId\": 1,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"total\": 1300\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"vendorId\": 2,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      3,\n" +
                                        "      4\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2,\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"total\": 1600\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"customerId\": 2,\n" +
                                        "    \"vendorId\": 3,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      5\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"total\": 850\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"customerId\": 3,\n" +
                                        "    \"vendorId\": 4,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      7\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"total\": 980\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 5,\n" +
                                        "    \"customerId\": 4,\n" +
                                        "    \"vendorId\": 3,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      5,\n" +
                                        "      6\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      1,\n" +
                                        "      2\n" +
                                        "    ],\n" +
                                        "    \"total\": 1950\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 6,\n" +
                                        "    \"customerId\": 4,\n" +
                                        "    \"vendorId\": 4,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"products\": [\n" +
                                        "      7\n" +
                                        "    ],\n" +
                                        "    \"qty\": [\n" +
                                        "      1\n" +
                                        "    ],\n" +
                                        "    \"total\": 490\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/carts/2"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 2,\n" +
                                        "  \"customerId\": 2,\n" +
                                        "  \"vendorId\": 2,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"products\": [\n" +
                                        "    3,\n" +
                                        "    4\n" +
                                        "  ],\n" +
                                        "  \"qty\": [\n" +
                                        "    2,\n" +
                                        "    1\n" +
                                        "  ],\n" +
                                        "  \"total\": 1600\n" +
                                        "}")
                );

        mockMvc.perform(post("/carts")
                .contentType("application/json")
                .content("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"customerId\": 2,\n" +
                        "  \"vendorId\": 1,\n" +
                        "  \"categoryId\": 1,\n" +
                        "  \"products\": [\n" +
                        "    1,\n" +
                        "    2\n" +
                        "  ],\n" +
                        "  \"qty\": [\n" +
                        "    2,\n" +
                        "    3\n" +
                        "  ]\n" +
                        "}\n"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 7,\n" +
                                        "  \"customerId\": 2,\n" +
                                        "  \"vendorId\": 1,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"products\": [\n" +
                                        "    1,\n" +
                                        "    2\n" +
                                        "  ],\n" +
                                        "  \"qty\": [\n" +
                                        "    2,\n" +
                                        "    3\n" +
                                        "  ],\n" +
                                        "  \"total\": 1900\n" +
                                        "}\n" +
                                        "\n")
                );

        mockMvc.perform(delete("/carts/4/remove"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 4,\n" +
                                        "  \"customerId\": 3,\n" +
                                        "  \"vendorId\": 4,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"products\": [\n" +
                                        "    7\n" +
                                        "  ],\n" +
                                        "  \"qty\": [\n" +
                                        "    2\n" +
                                        "  ],\n" +
                                        "  \"total\": 980\n" +
                                        "}")
                );

        mockMvc.perform(get("/customers"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"name\": \"Белов\",\n" +
                                        "    \"address\": \"Казань, Ямашева 52\",\n" +
                                        "    \"lon\": 49.124776,\n" +
                                        "    \"lat\": 55.828106\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"name\": \"Воробей\",\n" +
                                        "    \"address\": \"Казань, Мусина 54\",\n" +
                                        "    \"lon\": 49.124677,\n" +
                                        "    \"lat\": 55.832839\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"name\": \"Городец\",\n" +
                                        "    \"address\": \"Казань, Бутлерова 39\",\n" +
                                        "    \"lon\": 49.134307,\n" +
                                        "    \"lat\": 55.789006\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"name\": \"Давыдов\",\n" +
                                        "    \"address\": \"Казань, Профсоюзная 15\",\n" +
                                        "    \"lon\": 49.116403,\n" +
                                        "    \"lat\": 55.791511\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 5,\n" +
                                        "    \"name\": \"Матвеев\",\n" +
                                        "    \"address\": \"Казань, Чуйкова 63\",\n" +
                                        "    \"lon\": 49.140298,\n" +
                                        "    \"lat\": 55.836777\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/customers/2/"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 2,\n" +
                                        "  \"name\": \"Воробей\",\n" +
                                        "  \"address\": \"Казань, Мусина 54\",\n" +
                                        "  \"lon\": 49.124677,\n" +
                                        "  \"lat\": 55.832839\n" +
                                        "}")
                );

        mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"name\": \"Громов\",\n" +
                        "  \"address\": \"Казань, Тимирязева 4\"\n" +
                        "}"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 6,\n" +
                                        "  \"name\": \"Громов\",\n" +
                                        "  \"address\": \"Казань, Тимирязева 4\",\n" +
                                        "  \"lon\": 49.081522,\n" +
                                        "  \"lat\": 55.850803\n" +
                                        "}")
                );

        mockMvc.perform(get("/deliverers"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"name\": \"Смелов\",\n" +
                                        "    \"location\": \"Казань, Абсалямова 37\",\n" +
                                        "    \"lon\": 49.109549,\n" +
                                        "    \"lat\": 55.824673\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"name\": \"Жеглов\",\n" +
                                        "    \"location\": \"Казань, Амирхана 5\",\n" +
                                        "    \"lon\": 49.131288,\n" +
                                        "    \"lat\": 55.821254\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"name\": \"Багдарян\",\n" +
                                        "    \"location\": \"Казань, Баумана 68\",\n" +
                                        "    \"lon\": 49.131288,\n" +
                                        "    \"lat\": 55.821254\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"name\": \"Сванидзе\",\n" +
                                        "    \"location\": \"Казань, Коротченко 2\",\n" +
                                        "    \"lon\": 49.097835,\n" +
                                        "    \"lat\": 55.79387\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 5,\n" +
                                        "    \"name\": \"Рамирес\",\n" +
                                        "    \"location\": \"Казань, Горького 15\",\n" +
                                        "    \"lon\": 49.131091,\n" +
                                        "    \"lat\": 55.794097\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 6,\n" +
                                        "    \"name\": \"Броневой\",\n" +
                                        "    \"location\": \"Казань, Гривская 45\",\n" +
                                        "    \"lon\": 49.093352,\n" +
                                        "    \"lat\": 55.80826\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 7,\n" +
                                        "    \"name\": \"Ткач\",\n" +
                                        "    \"location\": \"Казань, Сулеймановой 7\",\n" +
                                        "    \"lon\": 49.088313,\n" +
                                        "    \"lat\": 55.81298\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/deliverers/2"))
                .andExpect(
                        content()
                                .json("  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"name\": \"Жеглов\",\n" +
                                        "    \"location\": \"Казань, Амирхана 5\",\n" +
                                        "    \"lon\": 49.131288,\n" +
                                        "    \"lat\": 55.821254\n" +
                                        "  }")
                );

        mockMvc.perform(post("/deliverers")
                .contentType("application/json")
                .content("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"name\": \"Полевой\",\n" +
                        "  \"location\": \"Казань, Абсалямова 23\"\n" +
                        "}"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 8,\n" +
                                        "  \"name\": \"Полевой\",\n" +
                                        "  \"location\": \"Казань, Абсалямова 23\",\n" +
                                        "  \"lon\": 49.109576,\n" +
                                        "  \"lat\": 55.821077\n" +
                                        "}")
                );

        mockMvc.perform(delete("/deliverers/7/remove"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 7,\n" +
                                        "  \"name\": \"Ткач\",\n" +
                                        "  \"location\": \"Казань, Сулеймановой 7\",\n" +
                                        "  \"lon\": 49.088313,\n" +
                                        "  \"lat\": 55.81298\n" +
                                        "}")
                );

        mockMvc.perform(get("/vendors"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"name\": \"Cafe La Vita\",\n" +
                                        "    \"address\": \"Казань, Чистопольская 19А\",\n" +
                                        "    \"lon\": 49.114795,\n" +
                                        "    \"lat\": 55.81997\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"name\": \"Агафредо\",\n" +
                                        "    \"address\": \"Казань, Галактионова 6\",\n" +
                                        "    \"lon\": 49.12633,\n" +
                                        "    \"lat\": 55.792706\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"name\": \"Утка в котелке\",\n" +
                                        "    \"address\": \"Казань, Дзержинского 13\",\n" +
                                        "    \"lon\": 49.12633,\n" +
                                        "    \"lat\": 55.792706\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"name\": \"Fusion of Asia\",\n" +
                                        "    \"address\": \"Казань, Лево-Булачная 24/1\",\n" +
                                        "    \"lon\": 49.107366,\n" +
                                        "    \"lat\": 55.791653\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/vendors/3"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 3,\n" +
                                        "  \"name\": \"Утка в котелке\",\n" +
                                        "  \"address\": \"Казань, Дзержинского 13\",\n" +
                                        "  \"lon\": 49.12633,\n" +
                                        "  \"lat\": 55.792706\n" +
                                        "}")
                );

        mockMvc.perform(post("/vendors")
                .contentType("application/json")
                .content("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"name\": \"Мясо в лаваше\",\n" +
                        "  \"address\": \"Казань, Мусина 10А\"\n" +
                        "}"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 5,\n" +
                                        "  \"name\": \"Мясо в лаваше\",\n" +
                                        "  \"address\": \"Казань, Мусина 10А\",\n" +
                                        "  \"lon\": 49.120167,\n" +
                                        "  \"lat\": 55.832869\n" +
                                        "}")
                );

        mockMvc.perform(delete("/vendors/5/remove"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 5,\n" +
                                        "  \"name\": \"Мясо в лаваше\",\n" +
                                        "  \"address\": \"Казань, Мусина 10А\",\n" +
                                        "  \"lon\": 49.120167,\n" +
                                        "  \"lat\": 55.832869\n" +
                                        "}")
                );

        mockMvc.perform(get("/products"))
                .andExpect(
                        content()
                                .json("[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 1,\n" +
                                        "    \"name\": \"pizza\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 500\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 1,\n" +
                                        "    \"name\": \"pasta\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 300\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 3,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 2,\n" +
                                        "    \"name\": \"pizza\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 600\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 4,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 2,\n" +
                                        "    \"name\": \"pasta\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 400\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 5,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 3,\n" +
                                        "    \"name\": \"утка\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 850\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 6,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 3,\n" +
                                        "    \"name\": \"оливье\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 550\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 7,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 4,\n" +
                                        "    \"name\": \"том ям\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 490\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 8,\n" +
                                        "    \"categoryId\": 1,\n" +
                                        "    \"vendorId\": 4,\n" +
                                        "    \"name\": \"курица терияки\",\n" +
                                        "    \"imageUrl\": \"url\",\n" +
                                        "    \"unitPrice\": 420\n" +
                                        "  }\n" +
                                        "]")
                );

        mockMvc.perform(get("/products/7"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 7,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"vendorId\": 4,\n" +
                                        "  \"name\": \"том ям\",\n" +
                                        "  \"imageUrl\": \"url\",\n" +
                                        "  \"unitPrice\": 490\n" +
                                        "}")
                );

        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"categoryId\": 1,\n" +
                        "  \"vendorId\": 3,\n" +
                        "  \"name\": \"Шашлык\",\n" +
                        "  \"unitPrice\": 400\n" +
                        "}"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 9,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"vendorId\": 3,\n" +
                                        "  \"name\": \"Шашлык\",\n" +
                                        "  \"imageUrl\": null,\n" +
                                        "  \"unitPrice\": 400\n" +
                                        "}")
                );

        mockMvc.perform(delete("/products/9/remove"))
                .andExpect(
                        content()
                                .json("{\n" +
                                        "  \"id\": 9,\n" +
                                        "  \"categoryId\": 1,\n" +
                                        "  \"vendorId\": 3,\n" +
                                        "  \"name\": \"Шашлык\",\n" +
                                        "  \"imageUrl\": null,\n" +
                                        "  \"unitPrice\": 400\n" +
                                        "}")
                );
    }
}


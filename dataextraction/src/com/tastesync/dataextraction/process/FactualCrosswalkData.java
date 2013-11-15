package com.tastesync.dataextraction.process;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;

import com.tastesync.dataextraction.db.dao.FactualCrosswalkDAO;
import com.tastesync.dataextraction.db.dao.FactualCrosswalkDAOImpl;
import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.json.factual.crosswalk.Datum;
import com.tastesync.dataextraction.json.factual.crosswalk.FactualCrosswalkDataElementVO;

import com.tastesync.db.pool.TSDataSource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;


public class FactualCrosswalkData {
    private static final String OPENTABLE_NAMESCPACE = "opentable";
    private FactualCrosswalkDAO factualCrosswalkDAO = new FactualCrosswalkDAOImpl();

    public FactualCrosswalkData() {
        super();
    }

    public void processFactualCrosswalkData(TSDataSource tsDataSource,
        Connection connection, Factual factual) throws TasteSyncException {
        List<String> factualIdList = factualCrosswalkDAO.getFactualIds(tsDataSource,
                connection);

        for (String factualId : factualIdList) {
            System.out.println("factualId=" + factualId);
            processSingleFactualCrosswalkData(tsDataSource, connection,
                factual, factualId);
        }
    }

    private void processSingleFactualCrosswalkData(TSDataSource tsDataSource,
        Connection connection, Factual factual, String factualId)
        throws TasteSyncException {
        try {
            // Get all Crosswalk data for a specific Places entity, using its Factual ID:
            ReadResponse readResponse = factual.fetch("crosswalk",
                    new Query().field("namespace").isEqual(OPENTABLE_NAMESCPACE)
                               .field("factual_id").isEqual(factualId));

            //{"version":3,"status":"ok","response":{"data":[{"factual_id":"24b7238f-54d5-4067-aa7c-712501c4c8c3","namespace":"opentable","namespace_id":"36796","url":"http://www.opentable.com/rest_profile.aspx?rid=36796"},{"factual_id":"24b7238f-54d5-4067-aa7c-712501c4c8c3","url":"http://www.opentable.com/the-wright?rid=36796","namespace":"opentable"}],"included_rows":2}}
            String jsonReadResponse = readResponse.getJson();

            System.out.println(jsonReadResponse);

            ObjectMapper mapper = new ObjectMapper();
            FactualCrosswalkDataElementVO factualCrosswalkDataElementVO = mapper.readValue(jsonReadResponse,
                    FactualCrosswalkDataElementVO.class);

            List<Datum> data = factualCrosswalkDataElementVO.getResponse()
                                                            .getData();

            if ((data == null) || data.isEmpty()) {
                System.out.println("factualId=" + factualId + " with no data.");

                try {
                    tsDataSource.begin();
                    factualCrosswalkDAO.addInvalidCrossWalkData(tsDataSource,
                        connection, factualId, factualId);
                    tsDataSource.commit();
                } catch (SQLException e) {
                    e.printStackTrace();

                    try {
                        tsDataSource.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    throw new TasteSyncException(e.getMessage());
                }

                return;
            }

            String openTableUrl = factualCrosswalkDataElementVO.getResponse()
                                                               .getData().get(0)
                                                               .getUrl();
            System.out.println(factualCrosswalkDataElementVO);
            System.out.println("openTableUrl=" + openTableUrl +
                " for factualId=" + factualId);

            String restaurantId = factualId;

            String reservationSourceId = factualCrosswalkDataElementVO.getResponse()
                                                                      .getData()
                                                                      .get(0)
                                                                      .getNamespace_id();

            try {
                tsDataSource.begin();
                factualCrosswalkDAO.addOpenTableFactualCrossWalkData(tsDataSource,
                    connection, restaurantId, factualId, OPENTABLE_NAMESCPACE,
                    reservationSourceId, openTableUrl);
                tsDataSource.commit();
            } catch (SQLException e) {
                e.printStackTrace();

                try {
                    tsDataSource.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                throw new TasteSyncException(e.getMessage());
            }

            System.out.println("*******************");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

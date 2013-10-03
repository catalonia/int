package com.tastesync.algo.model.vo;

import java.io.Serializable;


public class PiRecommendationsTopicMatchRateVO implements Serializable {
    private static final long serialVersionUID = 8275000965848972423L;
    private String recommendationId;
    private double topicMatchRate;

    public PiRecommendationsTopicMatchRateVO(String recommendationId,
        double topicMatchRate) {
        super();
        this.recommendationId = recommendationId;
        this.topicMatchRate = topicMatchRate;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public double getTopicMatchRate() {
        return topicMatchRate;
    }

    public int compare(PiRecommendationsTopicMatchRateVO o1,
        PiRecommendationsTopicMatchRateVO o2) {
        if (o1.getTopicMatchRate() == o2.getTopicMatchRate()) {
            return 0;
        }

        return (o1.getTopicMatchRate() > o2.getTopicMatchRate()) ? (-1) : 1;
    }

    @Override
    public String toString() {
        return "PiRecommendationsTopicMatchRateVO [recommendationId=" +
        recommendationId + ", topicMatchRate=" + topicMatchRate + "]";
    }
}

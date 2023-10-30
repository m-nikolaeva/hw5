package ru.gb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "LocalizedName"
    })

    public class City {

        @JsonProperty("LocalizedName")
        private String localizedName;

        @JsonProperty("LocalizedName")
        public String getLocalizedName() {
            return localizedName;
        }

        @JsonProperty("LocalizedName")
        public void setLocalizedName(String localizedName) {
            this.localizedName = localizedName;
        }
}

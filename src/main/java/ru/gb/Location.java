package ru.gb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
        "Key"
    })

    public class Location {

        @JsonProperty("Key")
        private String key;

        @JsonProperty("Key")
        public String getKey() {
            return key;
        }

        @JsonProperty("Key")
        public void setKey(String key) {
            this.key = key;
        }
    }

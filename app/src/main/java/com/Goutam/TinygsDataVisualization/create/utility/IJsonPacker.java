package com.Goutam.TinygsDataVisualization.create.utility;

import org.json.JSONException;
import org.json.JSONObject;

public interface IJsonPacker<K> {
    JSONObject pack() throws JSONException;

    K unpack(JSONObject obj) throws JSONException;
}

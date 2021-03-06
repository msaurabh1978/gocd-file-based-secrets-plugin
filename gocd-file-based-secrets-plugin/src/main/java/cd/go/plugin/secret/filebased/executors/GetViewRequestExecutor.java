/*
 * Copyright 2019 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.plugin.secret.filebased.executors;

import cd.go.plugin.secret.filebased.FileBasedSecretsPlugin;
import cd.go.plugin.secret.filebased.db.Util;
import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;

public class GetViewRequestExecutor {
    public GoPluginApiResponse execute() {
        int responseCode = DefaultGoApiResponse.SUCCESS_RESPONSE_CODE;
        HashMap<String, String> view = new HashMap<>();
        try {
            view.put("template", Util.readResourceAsString("/secrets.template.html"));
        } catch (Exception e) {
            responseCode = DefaultGoApiResponse.INTERNAL_ERROR;
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            FileBasedSecretsPlugin.LOGGER.error(errorMessage, e);
        }
        return new DefaultGoPluginApiResponse(responseCode, FileBasedSecretsPlugin.GSON.toJson(view));
    }
}

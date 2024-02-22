/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utilities;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import org.glassfish.jersey.client.ClientConfig;
import jakarta.ws.rs.client.ClientBuilder;

/**
 * Utility class for communication with the ServerController REST API
 */
public class ServerCommunication {

    public static String SERVER = "http://localhost:8080/";
    protected static String RAW_SERVER = "localhost:8080/";

    /**
     * The constructor for the ServerCommunication class
     */
    public ServerCommunication() { }

    /**
     * Method that checks if the server at SERVER is working.
     * @return boolean that holds true iff the server is working
     */
    public boolean checkServer() {
        try {
            return ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/connect") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Setter for the SERVER
     * @param server new server
     */
    public static void setServer(String server) {
        if (!server.endsWith("/")) {
            server += "/";
        }
        SERVER = server;
        RAW_SERVER = SERVER.substring(7);
    }
}
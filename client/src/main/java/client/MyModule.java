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
package client;

import client.scenes.AdminPanelController;
import client.scenes.ChangeServerController;
import client.scenes.QuestionController;
import client.scenes.QuestionControllerMP;
import client.scenes.SoloWaitingController;
import client.scenes.SplashController;
import client.scenes.TutorialController;
import client.scenes.WaitingController;
import client.utilities.AdminPanelCommunication;
import client.utilities.GridUtils;
import client.utilities.QuestionCommunication;
import client.utilities.ServerCommunication;
import client.utilities.UserSessionCommunication;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import client.scenes.MainCtrl;

public class MyModule implements Module {

    /**
     * Method used to configure all the binding configurations
     * @param binder An instance of the Binder class
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SplashController.class).in(Scopes.SINGLETON);
        binder.bind(ChangeServerController.class).in(Scopes.SINGLETON);
        binder.bind(QuestionController.class).in(Scopes.SINGLETON);
        binder.bind(QuestionControllerMP.class).in(Scopes.SINGLETON);
        binder.bind(SoloWaitingController.class).in(Scopes.SINGLETON);
        binder.bind(TutorialController.class).in(Scopes.SINGLETON);
        binder.bind(WaitingController.class).in(Scopes.SINGLETON);
        binder.bind(ServerCommunication.class).in(Scopes.SINGLETON);
        binder.bind(QuestionCommunication.class).in(Scopes.SINGLETON);
        binder.bind(AdminPanelCommunication.class).in(Scopes.SINGLETON);
        binder.bind(AdminPanelController.class).in(Scopes.SINGLETON);
        binder.bind(UserSessionCommunication.class).in(Scopes.SINGLETON);
        binder.bind(GridUtils.class).in(Scopes.SINGLETON);
    }
}
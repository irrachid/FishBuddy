package com.example.mayankrai.fishbuddy.dependencyinjection.component;

import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.dependencyinjection.module.ViewHolderModule;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerViewHolder;
import com.example.mayankrai.fishbuddy.screens.homescreen.feedscreen.recyclerviewadapter.FeedViewHolder;

import dagger.Component;

/* Copyright 2016 Patrick Löwenstein
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
 * limitations under the License. */
@PerViewHolder
@Component(dependencies = AppComponent.class, modules = {ViewHolderModule.class})
public interface ViewHolderComponent {

    Navigator navigator();

    void inject(FeedViewHolder viewHolder);

}

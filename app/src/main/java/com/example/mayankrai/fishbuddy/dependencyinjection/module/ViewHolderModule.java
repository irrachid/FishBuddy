package com.example.mayankrai.fishbuddy.dependencyinjection.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mayankrai.fishbuddy.base.navigator.ActivityNavigator;
import com.example.mayankrai.fishbuddy.base.navigator.Navigator;
import com.example.mayankrai.fishbuddy.dependencyinjection.qualifiers.ActivityContext;
import com.example.mayankrai.fishbuddy.dependencyinjection.scope.PerViewHolder;

import dagger.Module;
import dagger.Provides;

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
@Module
public class ViewHolderModule
{
    private final AppCompatActivity activity;

    public ViewHolderModule(View itemView) {
        activity = (AppCompatActivity) itemView.getContext();
    }

    @Provides
    @PerViewHolder
    @ActivityContext
    Context provideActivityContext() { return activity; }

    @Provides
    @PerViewHolder
    FragmentManager provideFragmentManager() { return activity.getSupportFragmentManager(); }

    @Provides
    @PerViewHolder
    Navigator provideNavigator() { return new ActivityNavigator(activity); }
}

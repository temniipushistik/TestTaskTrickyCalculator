/*
 * Copyright (C) 2020 Gennadii Koshelev
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at KoshelevGennadii1989@gmail.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HiddenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //switch on navigated menu
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
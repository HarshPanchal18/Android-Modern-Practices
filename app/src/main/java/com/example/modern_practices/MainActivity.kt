package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewModelScope
import com.example.modern_practices.container.Component
import com.example.modern_practices.scopes.ComponentA
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.scope.activityScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class MainActivity : ComponentActivity(),KoinScopeComponent {

    private val component = Component()

    override val scope: Scope by activityScope()
    //override val scope: Scope by activityRetainedScope() - used on configuration changes
    private val componentA : ComponentA by scope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernPracticesTheme {
                Surface {
                    //component.car.getCar()
                    //component.main.getDemo()
                    //component.mainViewModel.getTest()
                    //component.retrofitViewModel.getPost()
                    //component.roomViewModel.getAllUser()
                    //component.users.getUser()
                    componentA.getA()
                }
            }
        }
    }
}

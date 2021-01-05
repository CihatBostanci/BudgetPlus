package com.example.budgetplus.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.budgetplus.BaseActivity
import com.example.budgetplus.MainActivity
import com.example.budgetplus.R
import com.example.budgetplus.databinding.ActivitySplashBinding
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.utils.*
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject


class SplashActivity : BaseActivity() {
    private val SPLASHTAG = "SPLASHTAG"

        // Initalization property
    private val SPLASH_TIME_OUT: Long = 6000
    private var itemMap = mutableMapOf<Int, ImageView>()
    lateinit var animation: Animation
    private var width = 0
    private var height = 0
    private var set: AnimatorSet? = null

    //View Binding
    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel: Lazy<SplashViewModel> = inject(SplashViewModel::class.java)

    private  var userInfoResponseModelTemp : UserInfoResponseModel? = null
    private  var groupDetailsResponseModelTemp : GroupDetailsResponseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initObservers()
        getUserInfoService()
        getGroupDetailsService()
        initUI()


        val intent  = Intent(this, MainActivity::class.java)

        //To Start Main Activity
        Log.d(SPLASHTAG, intent.extras.toString())

        Handler().postDelayed(
            {
                userInfoResponseModelTemp?.let {
                    intent.putExtraJson(USERINFORESPONSEMODEL, it)
                }
                groupDetailsResponseModelTemp?.let {
                    intent.putExtraJson(GROUPDETAILSRESPONSEMODEL, it)
                }
                startActivity(intent)
                finish()
            },
            SPLASH_TIME_OUT
        )
    }

    private fun initObservers() {
        splashViewModel.value.userInfo.observe(this, _userObserver)
        splashViewModel.value.groupDetails.observe(this, _groupDetailsObserver)
    }

    private fun getGroupDetailsService() {
        splashViewModel.value.getGroupDetails()
        //groupViewModel.getGroupDetails().observe(this, _groupDetailsObserver)
    }

    private fun getUserInfoService() {
        splashViewModel.value.getUserInfo()
        //accountViewModel.getUserInfos().observe(this,_userObserver)
    }

    private fun initUI() {
        //Animation Load
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.IWPlusLogo.animation = animationRotate
        binding.logo.animation = animation

        itemMap = mutableMapOf(
            0 to binding.IWBubble0,
            1 to binding.IWBubble1,
            2 to binding.IWBubble2,
            3 to binding.IWBubble3
        )

        //To trigger bubbles draw and animations
        bubbleMove()
        binding.logo.bringToFront()
        binding.IWPlusLogo.bringToFront()
    }

    private fun randomAnimateXAndYAxis(
        view: ImageView,
        axisType: String,
        next: Float
    ): ObjectAnimator {
        return if (axisType == "x") {
            ObjectAnimator.ofFloat(view, axisType, view.x, next)
        } else ObjectAnimator.ofFloat(view, axisType, view.y, next)

    }

    private fun bubbleMove() {


        drawAllImageViewAsCircle()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels


        set = createAnimation()
        set?.start()

        set?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {

                val set = animationSet()
                set.duration = 3000
                set.start()

            }
        })
    }

    private fun drawAllImageViewAsCircle() {
        binding.IWBubble0.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble0ColorDark),
                ContextCompat.getColor(this, R.color.bubble0ColorDark)
            )
        binding.IWBubble1.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble1ColorDark),
                ContextCompat.getColor(this, R.color.bubble1ColorDark)
            )
        binding.IWBubble2.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble2ColorDark),
                ContextCompat.getColor(this, R.color.bubble2ColorDark)
            )
        binding.IWBubble3.background =
            drawCircle(
                ContextCompat.getColor(this, R.color.bubble3ColorDark),
                ContextCompat.getColor(this, R.color.bubble3ColorDark)
            )
    }

    private fun drawCircle(backgroundColor: Int, borderColor: Int): GradientDrawable? {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        shape.setColor(backgroundColor)
        shape.setStroke(10, borderColor)
        return shape
    }

    private fun randomNumber(range: Int) = (0..range).random().toFloat()

    // generated random from 1 to 9 included
    private fun createAnimation(): AnimatorSet? {

        val set = animationSet()
        set.duration = 2000

        return set
    }

    private fun animationSet(): AnimatorSet {
        val set = AnimatorSet()
        val animationList = mutableListOf<ObjectAnimator>()
        for (i in 0..4) {
            if (itemMap[i] != null) {
                val animationY = randomAnimateXAndYAxis(
                    itemMap[i]!!, "y",
                    randomNumber(height)
                )
                //animationY.duration = 3000
                val animationX = randomAnimateXAndYAxis(
                    itemMap[i]!!, "x", randomNumber(width)
                )
                //animationX.duration = 3000
                animationList.add(animationX)
                animationList.add(animationY)
                //println(randomNumber(height).toString() + ": " + randomNumber(width))
            }
        }
        set.playTogether(
            animationList as Collection<Animator>?
        )
        return set
    }

    private val _userObserver = Observer<Resource<ResponseBody>>{
        when(it.status){
            Status.SUCCESS -> {
                hide()
                it.data?.let { userInfo ->
                    val dataModel = IntentUtil.gson.fromJson(
                        userInfo.charStream(), UserInfoResponseModel::class.java
                    )
                    userInfoResponseModelTemp = dataModel
                    Log.d(SPLASHTAG, dataModel.toString())
                }
            }
            Status.ERROR -> {
                hide()
                //showToast(it.messag
            }
            Status.LOADING -> {
                //show()
            }
        }
    }

    private val _groupDetailsObserver = Observer<Resource<ResponseBody>>{
        when(it.status){
            Status.SUCCESS -> {
                hide()
                it.data?.let { groupDetails ->
                    val dataModel = IntentUtil.gson.fromJson(
                        groupDetails.charStream(), GroupDetailsResponseModel::class.java
                    )
                    groupDetailsResponseModelTemp = dataModel
                    Log.d(SPLASHTAG, dataModel.toString())
                }
            }
            Status.ERROR -> {
                hide()
                //showToast(it.message)

            }
            Status.LOADING -> {
                //show()
            }
        }
    }

}
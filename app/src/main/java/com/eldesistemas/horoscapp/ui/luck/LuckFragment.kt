package com.eldesistemas.horoscapp.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.eldesistemas.horoscapp.R
import com.eldesistemas.horoscapp.databinding.FragmentLuckBinding
import com.eldesistemas.horoscapp.ui.core.listeners.OnSwipeTouchListener
import com.eldesistemas.horoscapp.ui.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class LuckFragment : Fragment() {

    private var _binding: FragmentLuckBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var randomCardProvider: RandomCardProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initUI() {

        preparePrediction()
        initListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {

        binding.ivRoulette.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {

            override fun onSwipeRight() {
                spinRoulette()
            }

            override fun onSwipeLeft() {
                spinRoulette()
            }
        })

    }

    private fun spinRoulette() {

        val degress = Random().nextInt(1440) + 360
        val animator = ObjectAnimator.ofFloat(binding.ivRoulette, View.ROTATION, 0f, degress.toFloat())

        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd { slideCard() }
        animator.start()
    }

    private fun slideCard() {

        val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        slideUpAnimation.setAnimationListener(object: Animation.AnimationListener {

            override fun onAnimationStart(p0: Animation?) {

                binding.ivReverse.isVisible = true
            }

            override fun onAnimationEnd(p0: Animation?) {
                growAnimation()
            }

            override fun onAnimationRepeat(p0: Animation?) { }

        })

        binding.ivReverse.startAnimation(slideUpAnimation)
    }

    private fun growAnimation() {

        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)

        growAnimation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) { }

            override fun onAnimationEnd(p0: Animation?) {
                binding.ivReverse.isVisible = false
                showPremonitionView()
            }

            override fun onAnimationRepeat(p0: Animation?) { }

        })

        binding.ivReverse.startAnimation(growAnimation)
    }

    private fun showPremonitionView() {

        val appearAnimation = AlphaAnimation(0.0f, 1.0f)
        val disappearAnimation = AlphaAnimation(1.0f, 0.0f)

        appearAnimation.duration = 1000
        disappearAnimation.duration = 200
        disappearAnimation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) { }

            override fun onAnimationEnd(p0: Animation?) {
                binding.preview.isVisible = false
                binding.prediction.isVisible = true
            }

            override fun onAnimationRepeat(p0: Animation?) { }

        })

        binding.preview.startAnimation(disappearAnimation)
        binding.prediction.startAnimation(appearAnimation)
    }

    private fun preparePrediction() {

        val currentLuck = randomCardProvider.getLucky()

        currentLuck?.let {luck ->

            val currentPrediction = getString(luck.text)

            binding.tvLucky.text = currentPrediction
            binding.ivLuckyCard.setImageResource(luck.image)
            binding.tvShare.setOnClickListener{ sharePrediction(currentPrediction) }
        }
    }

    private fun sharePrediction(prediction: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prediction)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)

        startActivity(shareIntent)
    }
}
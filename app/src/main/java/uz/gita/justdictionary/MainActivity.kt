package uz.gita.justdictionary

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent

import android.content.pm.PackageManager
import android.net.Uri

import androidx.core.content.ContextCompat

import android.os.Build
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)
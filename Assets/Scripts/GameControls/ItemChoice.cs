﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

/// <summary>
/// Script for choosing an item
/// </summary>
public class ItemChoice : MonoBehaviour
{
    Text difficulty;

    /// <summary>
    /// Increases players damage
    /// </summary>
    public void GetAttack()
    {
        difficulty = GameObject.Find("Attack/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        float attack = PlayerPrefs.GetFloat("attack", 0);
        PlayerPrefs.SetFloat("attack", attack + 5);
        SceneManager.LoadScene("QuestionDisplay");
    }

    /// <summary>
    /// Increases players health
    /// </summary>
    public void GetHealth()
    {
        difficulty = GameObject.Find("Health/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        PlayerPrefs.SetInt("health", 100);
        SceneManager.LoadScene("QuestionDisplay");
    }

    /// <summary>
    /// Increases players attack speed
    /// </summary>
    public void GetSpeed()
    {
        difficulty = GameObject.Find("Speed/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        float speed = PlayerPrefs.GetFloat("speed", 0);
        PlayerPrefs.SetFloat("speed", speed + 5);
        SceneManager.LoadScene("QuestionDisplay");
    }
}

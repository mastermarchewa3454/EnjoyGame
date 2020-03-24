using System.Collections;
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
        SceneManager.LoadScene("QuestionDisplay");
    }

    /// <summary>
    /// Increases players health
    /// </summary>
    public void GetHealth()
    {
        difficulty = GameObject.Find("Health/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        SceneManager.LoadScene("QuestionDisplay");
    }

    /// <summary>
    /// Increases players attack speed
    /// </summary>
    public void GetSpeed()
    {
        difficulty = GameObject.Find("Speed/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        SceneManager.LoadScene("QuestionDisplay");
    }
}

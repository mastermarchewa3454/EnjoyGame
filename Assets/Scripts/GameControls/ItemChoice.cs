using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ItemChoice : MonoBehaviour
{
    Text difficulty;

    public void GetAttack()
    {
        difficulty = GameObject.Find("Attack/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetHealth()
    {
        difficulty = GameObject.Find("Health/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetSpeed()
    {
        difficulty = GameObject.Find("Speed/Text").GetComponent<Text>();
        PlayerPrefs.SetString("difficulty", difficulty.text);
        SceneManager.LoadScene("QuestionDisplay");
    }
}

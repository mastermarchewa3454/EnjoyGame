using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ItemChoice : MonoBehaviour
{
    public void GetHealth()
    {
        PlayerPrefs.SetString("difficulty", "easy");
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetAttack()
    {
        PlayerPrefs.SetString("difficulty", "medium");
        SceneManager.LoadScene("QuestionDisplay");
    }

    public void GetSpeed()
    {
        PlayerPrefs.SetString("difficulty", "hard");
        SceneManager.LoadScene("QuestionDisplay");
    }
}

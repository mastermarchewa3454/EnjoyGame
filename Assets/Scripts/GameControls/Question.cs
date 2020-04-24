using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Handles correct / wrong answers
/// </summary>
public class Question : MonoBehaviour
{
    /// <summary>
    /// Back button is clicked
    /// </summary>
    public void GetBack()
    {
        SceneManager.LoadScene("Item Choice");
    }

    /// <summary>
    /// User answers correctly, and item effects are applied.
    /// </summary>
    public void GetCorrect()
    {
        string attribute = PlayerPrefs.GetString("temp");
        switch (attribute)
        {
            case "attack":
                int attack = PlayerPrefs.GetInt("attack", 10);
                PlayerPrefs.SetInt("attack", attack + 5);
                break;
            case "health":
                PlayerPrefs.SetInt("health", 100);
                break;
            case "speed":
                float speed = PlayerPrefs.GetFloat("speed", 5);
                PlayerPrefs.SetFloat("speed", speed + 1);
                break;
        }
        SceneManager.LoadScene("Item room");
    }

    /// <summary>
    /// User answers wrongly, nothing happens
    /// </summary>
    public void GetWrong()
    {
        SceneManager.LoadScene("Item room");
    }
}
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Question : MonoBehaviour
{
    public void GetBack()
    {
        SceneManager.LoadScene("Item Choice");
    }

    public void GetCorrect()
    {
        string stat = PlayerPrefs.GetString("stat", null);
        switch (stat)
        {
            case "attack":
                int attack = PlayerPrefs.GetInt("damage", 10) + 5;
                PlayerPrefs.SetInt("damage", attack);
                Debug.Log("Attack : " + attack);
                break;
            case "health":
                PlayerPrefs.SetInt("health", 100);
                Debug.Log("Health : 100");
                break;
            case "speed":
                float speed = PlayerPrefs.GetFloat("speed", 5f) + 1;
                PlayerPrefs.SetFloat("speed", speed);
                Debug.Log("Speed : " + speed);
                break;
            default:
                Debug.Log("Invalid stat : " + stat);
                break;
        }
        SceneManager.LoadScene("Item room");
    }

    public void GetWrong()
    {
        SceneManager.LoadScene("Item room");
    }
}
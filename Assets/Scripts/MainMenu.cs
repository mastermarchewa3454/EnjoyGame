using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    public void Start()
    {
        PlayerPrefs.DeleteAll();
    }


    public void PlayGame()
    {
        SceneManager.LoadScene("Level 1");
    }

    public void Leaderboard()
    {
        SceneManager.LoadScene("Leaderboard");
    }

    public void QuitGame()
    {
        Debug.Log("Sucessfully Exitted");
        Application.Quit();
        
    }

    public void ReturnMainMenu()
    {
        SceneManager.LoadScene("StartScreen");
    }

    public void EnterSocialMenu()
    {
        SceneManager.LoadScene("Social");
    }

    public void EnterCustomLobby()
    {
        SceneManager.LoadScene("QnA");
    }
}

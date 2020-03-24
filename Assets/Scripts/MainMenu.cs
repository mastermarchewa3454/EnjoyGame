using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{

    public void PlayGame()
    {
<<<<<<< Updated upstream
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
=======
        SceneManager.LoadScene("CharacterSelection");
>>>>>>> Stashed changes
    }

    public void Leaderboard()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 4);
    }

    public void QuitGame()
    {
        Debug.Log("Exit Successful!");
        Application.Quit();
        
    }

    public void ReturnMainMenu()
    {
        SceneManager.LoadScene(0);
    }

    public void EnterSocialMenu()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 6);
    }

    public void EnterCustomLobby()
    {
        SceneManager.LoadScene(5);
    }
}

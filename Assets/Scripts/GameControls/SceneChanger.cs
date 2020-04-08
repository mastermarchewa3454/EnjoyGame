using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Scene manager
/// </summary>
public class SceneChanger : MonoBehaviour
{

    GameObject player;
    Health playerHealth;

    int theCurrentScene;

    float timer;
    int level;

    /// <summary>
    /// Gets player components
    /// </summary>
    void Start()
    {
        player = GameObject.Find("Player");
        if (player != null)
        {
            playerHealth = player.GetComponent<Health>();
            level = PlayerPrefs.GetInt("level", 1);
        }
    }

    /// <summary>
    /// Changes to next game scene.
    /// Saves any relevant data before transition.
    /// </summary>
    public void ChangeToNextScene()
    {
        PlayerPrefs.SetInt("health", playerHealth.GetCurrHealth());
        PlayerPrefs.SetInt("level", level + 1);

        if (level % 3 == 2)
            PlayerPrefs.SetInt("treasure", 1);
        if (level == 60)
            ChangeToEndScene();

        theCurrentScene = SceneManager.GetActiveScene().buildIndex;
        if (level % 3 == 0)
        {
            SceneManager.LoadScene(theCurrentScene - 2);
        }
        else
        {
            SceneManager.LoadScene(theCurrentScene + 1);
        }
    }

    /// <summary>
    /// Returns to main menu
    /// </summary>
    public void ChangeToStartScene()
    {
        SceneManager.LoadScene("StartScreen");
    }

    public void ChangeToItemRoom()
    {
        SceneManager.LoadScene("Item room");
    }

    /// <summary>
    /// Goes to end scene
    /// </summary>
    public void ChangeToEndScene()
    {
        SceneManager.LoadScene("GameEnd");
    }

    /// <summary>
    /// Closes the application
    /// </summary>
    public void QuitGame()
    {
        Application.Quit();

    }
}

using Photon.Pun;
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

    // for multi 
    public static bool isDuoMode = false;
    private PhotonView pV;
    GameObject[] players;
    int  playerHealth1;
    int playerHealth2;
    /// <summary>
    /// Gets player components
    /// </summary>
    void Start()
    {
        if(!isDuoMode)
        {
            player = GameObject.Find("Player");
            if (player != null)
            {
                playerHealth = player.GetComponent<Health>();
                level = PlayerPrefs.GetInt("level", 1);
            }
        }        
    }
    /// <summary>
    /// Changes to next game scene.
    /// Saves any relevant data before transition.
    /// </summary>
    public void ChangeToNextScene()
    {
        if (level == 60)
        {
            ChangeToEndScene();
        }

        if(!isDuoMode)
        {           
            PlayerPrefs.SetInt("health", playerHealth.GetCurrHealth());
            PlayerPrefs.SetInt("level", level + 1);

            if (level % 3 == 2)
                PlayerPrefs.SetInt("treasure", 1);
        }
        else
        {
            players = GameObject.FindGameObjectsWithTag("Player");
            Debug.Log("Objects with tag players:" + players.Length);
            playerHealth1 = players[0].GetComponent<Health>().GetCurrHealth();
            playerHealth2 = players[1].GetComponent<Health>().GetCurrHealth();
        }

        theCurrentScene = SceneManager.GetActiveScene().buildIndex;

        if (!isDuoMode)
        {
            if (level % 3 == 0)
            {
                SceneManager.LoadScene(theCurrentScene - 2);
            }
            else
            {
                SceneManager.LoadScene(theCurrentScene + 1);
            }
        }
        else
        {
            if (level % 3 == 0)
            {
                var scene = SceneManager.GetSceneByBuildIndex(theCurrentScene - 2);
                MultiplayerSettings.multiSettings.multiScene = scene.name;
            }
            else
            {
                var scene = SceneManager.GetSceneByBuildIndex(theCurrentScene + 1);
                MultiplayerSettings.multiSettings.multiScene = scene.name;
            }
            PhotonNetwork.LoadLevel(MultiplayerSettings.multiSettings.multiScene);

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
        if(isDuoMode)
        {
            PhotonNetwork.LoadLevel("GameEnd");
        }
    }

    /// <summary>
    /// Closes the application
    /// </summary>
    public void QuitGame()
    {
        Application.Quit();

    }

    public int getPlayerHealth1()
    {
        return playerHealth1;
    }
    public int getPlayerHealth2()
    {
        return playerHealth2;
    }
}

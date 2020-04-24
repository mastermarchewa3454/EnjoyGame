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

    // for duoMode
    public static bool isDuoMode = false; // set to duoMode
    private PhotonView pV; // get photonview
    GameObject[] players; // get players for the multiplayer
    int  playerHealth1;
    int playerHealth2;
    /// <summary>
    /// Gets player components, if it is not duoMode
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
    /// If it is multiplayer, save the health value of player and get the name of the scene.
    /// Get the name of the scene.
    /// </summary>
    public void ChangeToNextScene()
    {
        if (level == 60)
        {
            Debug.Log("Done");
            ChangeToEndScene();
        }
        else
        {
            if (!isDuoMode)
            {
                PlayerPrefs.SetInt("health", playerHealth.GetCurrHealth());
                PlayerPrefs.SetInt("level", level + 1);

                if (level % 3 == 2)
                    PlayerPrefs.SetInt("treasure", 1);
            }
            else
            {
                level++;
                players = GameObject.FindGameObjectsWithTag("Player");
                Debug.Log("Objects with tag players:" + players.Length);
                PhotonRoom.theRoom.playerHealth1 = players[0].GetComponent<Health>().GetCurrHealth();
                PhotonRoom.theRoom.playerHealth2 = players[1].GetComponent<Health>().GetCurrHealth();
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
                    string sceneName = System.IO.Path.GetFileNameWithoutExtension(SceneUtility.GetScenePathByBuildIndex(theCurrentScene - 2));
                    MultiplayerSettings.multiSettings.multiScene = sceneName;
                }
                else
                {
                    string sceneName = System.IO.Path.GetFileNameWithoutExtension(SceneUtility.GetScenePathByBuildIndex(theCurrentScene + 1));
                    MultiplayerSettings.multiSettings.multiScene = sceneName;
                }              
                PhotonNetwork.LoadLevel(MultiplayerSettings.multiSettings.multiScene);

            }
        }
    }

    /// <summary>
    /// Returns to main menu
    /// </summary>
    public void ChangeToStartScene()
    {
        SceneManager.LoadScene("StartScreen");
    }
    /// <summary>
    /// Load Item room
    /// </summary>
    public void ChangeToItemRoom()
    {
        SceneManager.LoadScene("Item room");
    }

    /// <summary>
    /// Goes to end scene
    /// </summary>
    public void ChangeToEndScene()
    {
        if(isDuoMode)
        {
            MultiplayerSettings.multiSettings.multiScene = "GameEnd";
            PhotonNetwork.LoadLevel("GameEnd");
        }
        else
        {
            SceneManager.LoadScene("GameEnd");
        }
    }

    /// <summary>
    /// Closes the application
    /// </summary>
    public void QuitGame()
    {
        Application.Quit();

    }
}

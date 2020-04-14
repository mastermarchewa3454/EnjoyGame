using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Script for handling level transitions
/// </summary>
public class NextLevelDoor : MonoBehaviourPunCallbacks, IInRoomCallbacks
{
    SceneChanger sceneChanger;
    public static bool isDuoMode = false;
    private PhotonView pV;
    /// <summary>
    /// Finds the controller for scene transitions
    /// </summary>
    public void Start()
    {
        sceneChanger = FindObjectOfType<SceneChanger>();
        if(isDuoMode)
        {
            pV = GetComponent<PhotonView>();
        }
    }

    /// <summary>
    /// Checks for collision between player and door
    /// </summary>
    /// <param name="collision">Collision2D object</param>
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            // Only allows player to pass once all enemies are dead / treasure is taken
            GameObject[] allEnemies = GameObject.FindGameObjectsWithTag("Enemy");
            if (allEnemies.Length == 0 && PlayerPrefs.GetInt("treasure", 0) == 0)
            {               
                  sceneChanger.ChangeToNextScene();                                
            }
                
        }
    }
}

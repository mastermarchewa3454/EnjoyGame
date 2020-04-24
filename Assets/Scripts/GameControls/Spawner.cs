using Photon.Pun;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

/// <summary>
/// Spawner class to spawn in players
/// </summary>
public class Spawner : MonoBehaviour
{
    [SerializeField]
    GameObject archer;

    [SerializeField]
    GameObject mole;

    [SerializeField]
    GameObject treant;

    GameObject player;
    GameObject player2;

    public static bool isDuoMode = false; 
    
    /// <summary>
    ///Instantiates players
    /// </summary>
    void Start()
    {     
        if(!isDuoMode)
        {
            switch (PlayerPrefs.GetInt("character", 1))
            {
                case 1: player = archer; break;
                case 2: player = mole; break;
                case 3: player = treant; break;
                default: player = archer; break;
            }


            player = Instantiate(player, transform.position, Quaternion.identity);
            player.name = "Player";
        }              
    }

}

using Photon.Pun;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class PhotonPlayer : MonoBehaviour
{
    // initialize player for the gameobject 
    private PhotonView pV;
    public GameObject myCharacter;
    private int index = 1;
    void Start()
    {
        pV = GetComponent<PhotonView>();
        
        float yValue = Random.Range(-0.5f, 0.5f); // random starting position for the player
        if (pV.IsMine) // check if the given player belongs to given user
        {
            myCharacter = PhotonNetwork.Instantiate(Path.Combine("ForMulti", "Player"), new Vector3(-5f, yValue ,0f), Quaternion.identity, 0); //  initialize the player
            myCharacter.name = "Player";
            Debug.Log("New player was created");
        }
    }
}

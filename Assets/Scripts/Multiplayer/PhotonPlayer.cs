using Photon.Pun;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class PhotonPlayer : MonoBehaviour
{
    private PhotonView pV;
    public GameObject myCharacter;
    // Start is called before the first frame update
    void Start()
    {
        pV = GetComponent<PhotonView>();
        float yValue = Random.Range(-0.5f, 0.5f);
        if (pV.IsMine)
        {
            myCharacter = PhotonNetwork.Instantiate(Path.Combine("ForMulti", "Player"), new Vector3(-5f, yValue ,0f), Quaternion.identity, 0);
            myCharacter.name = "Player";
            Debug.Log("New player was created");
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

public class CustomLobbyID : MonoBehaviour
{
    public TMP_Text customlobbyID;

    public void Start()
    {
        var ID = PlayerPrefs.GetString("SavedCLI");
        Debug.Log("Your custom lobby id is " + ID);
        customlobbyID.text = "Your Custom Lobby has been successfully created with ID : " + ID;
    }
}

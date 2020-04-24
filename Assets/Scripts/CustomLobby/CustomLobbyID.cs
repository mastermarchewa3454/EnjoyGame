using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;


/// <summary>
/// The class CustomLobbyID will save the custom lobby id in PlayerPrefs and output the customlobbyID upon
/// registering it.
/// </summary>
public class CustomLobbyID : MonoBehaviour
{
    /// <summary>
    /// Instantiation of TMP_Text customLobbyID so as to change the text of the customLobbyID after getting it from database.
    /// </summary>
    public TMP_Text customlobbyID;
    /// <summary>
    /// The Start function will be called when the scene is first being displayed.
    /// It will display the custom lobby ID that will be returned from the string "SavedCLI".
    /// </summary>
    public void Start()
    {
        var ID = PlayerPrefs.GetString("SavedCLI");
        Debug.Log("Your custom lobby id is " + ID);
        customlobbyID.text = "Your Custom Lobby has been successfully created with ID : " + ID;
    }
}

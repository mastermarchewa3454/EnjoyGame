using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using System.IO;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.SceneManagement;

/// <summary>
/// Script for creating the lobby.
/// </summary>
public class Lobby : MonoBehaviourPunCallbacks
{
    public static Lobby lobby;
    public GameObject searchButton;
    public GameObject cancelButton;
    public GameObject playButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
    public static Spawner spawner;

    private void Awake()
    {
        lobby = this; // Creates singleton
    }
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server        
        PhotonNetwork.NickName = "Hans";
        waitingText.SetText("");
        playButton.SetActive(false);
        searchButton.SetActive(false);
        cancelButton.SetActive(false);
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
        PhotonNetwork.AutomaticallySyncScene = true;
        searchButton.SetActive(true);
    }

    /// <summary>
    /// When user clicks Search room button.
    /// </summary>
    public void OnSearchButtonClick()
    {
        searchButton.SetActive(false);
        cancelButton.SetActive(true);
        waitingText.SetText("Waiting...");
        CreateRoom();
    }

    /// <summary>
    /// Create Room and set parameter of this room.
    /// </summary>
    void CreateRoom()
    {     
        Debug.Log("Trying to create a new room");
        int roomID = Random.Range(0, 1000);
        RoomOptions roomOptions = new RoomOptions()
        {
            IsVisible = true,
            IsOpen = true,
            MaxPlayers = 2,
        };    
        lobbyID.SetText("LOBBY ID: " + roomID.ToString());
        PhotonNetwork.CreateRoom("Room" + roomID, roomOptions);
    }

    /// <summary>
    /// When the new room is created.
    /// </summary>
    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        
    }

    /// <summary>
    /// When other player enter the room.
    /// </summary>
    /// <param name="newPlayer"></param>
    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        waitingText.SetText("Connected to " + newPlayer.NickName);
        cancelButton.SetActive(false);
    }

    /// <summary>
    /// Signal when new room is created.
    /// </summary>
    public override void OnCreatedRoom()
    {
        Debug.Log("New room has been created");
    }

    /// <summary>
    /// Signal when there is an error about creating new room.
    /// </summary>
    /// <param name="returnCode"></param>
    /// <param name="message"></param>
    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room already exists");
        CreateRoom();
    }

    /// <summary>
    /// When user clicks Cancel button.
    /// </summary>
    public void OnCancelButtonClick()
    {
        cancelButton.SetActive(false);
        searchButton.SetActive(true);
        waitingText.SetText("");
        lobbyID.SetText("LOBBY ID: <>");        
        PhotonNetwork.LeaveRoom();
    }

    /// <summary>
    /// When user clicks Back button in Create Lobby.
    /// </summary>
    public void OnBackButtonClick()
    {
        if (PhotonNetwork.IsConnected)
        {
            PhotonNetwork.LeaveRoom();
        }
        if (PhotonRoom.theRoom != null)
        {
            Destroy(PhotonRoom.theRoom.gameObject);
        }
        setToSingleMode();   
    }

    /// <summary>
    /// Set the parameters in other class to play single mode.
    /// </summary>
    void setToSingleMode()
    {
        Spawner.isDuoMode = false;
        PlayerMovement.isDuoMode = false;
        FireController.isDuoMode = false;
        Health.isDuoMode = false;
    }
}



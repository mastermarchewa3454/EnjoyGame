using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using System.IO;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.SceneManagement;

public class Lobby : MonoBehaviourPunCallbacks
{
    public static Lobby lobby;
    public SceneChanger sceneChanger;
    public GameObject searchButton;
    public GameObject cancelButton;
    public GameObject playButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
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
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
        PhotonNetwork.AutomaticallySyncScene = true;
        searchButton.SetActive(true);
    }

    public void OnSearchButtonClick()
    {
        searchButton.SetActive(false);
        cancelButton.SetActive(true);
        waitingText.SetText("Waiting...");
        CreateRoom();
    }
    
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

    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        
    }
    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        waitingText.SetText("Connected to " + newPlayer.NickName);
        cancelButton.SetActive(false);
        playButton.SetActive(true);
    }
    public override void OnCreatedRoom()
    {
        Debug.Log("New room has been created");
    }
    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room already exists");
        CreateRoom();
    }

    public void OnCancelButtonClick()
    {
        cancelButton.SetActive(false);
        searchButton.SetActive(true);
        waitingText.SetText("");
        lobbyID.SetText("LOBBY ID: <>");
        PhotonNetwork.LeaveRoom();
    }
    public void OnPlayButtonClick()
    {
        PhotonNetwork.LoadLevel("StageSelection");
    }

    // Update is called once per frame
    void Update()
    {
    }
}



using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class Lobby : MonoBehaviourPunCallbacks
{
    public static Lobby lobby;

    public GameObject searchButton;
    public GameObject cancelButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
    private void Awake()
    {
        lobby = this; // Creates singleton
    }
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingText.SetText("");
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
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
            MaxPlayers = 2
        };
        lobbyID.SetText("LOBBY ID: " + roomID);
        PhotonNetwork.CreateRoom("Room" + roomID, roomOptions);
        Debug.Log("New room" + roomID + " is created");
    }

    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
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
        PhotonNetwork.LeaveRoom();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}

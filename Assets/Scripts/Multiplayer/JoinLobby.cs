using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class JoinLobby : MonoBehaviourPunCallbacks
{
    // Start is called before the first frame update
    public static JoinLobby lobby;
    public GameObject enterRoomButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
    public TextMeshProUGUI username;
    private void Awake()
    {
        lobby = this;
    }
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingText.SetText("");
        lobbyID.SetText("");
        username.SetText("");
    }
    public void OnEnterRoomButtonClick()
    {
        if(lobbyID.GetParsedText().Equals(""))
        {
            waitingText.SetText("Write lobbyID");
        }
        else if(username.GetParsedText().Equals(""))
        {
            waitingText.SetText("Write username");
        }
        else
        {
            string roomID = lobbyID.GetParsedText();
            string user = username.GetParsedText();
            waitingText.SetText("Waiting...");
            PhotonNetwork.NickName = user;
            Debug.Log("The roomID is " + roomID);
            Debug.Log("The username is: " + user);
            PhotonNetwork.JoinRoom(roomID);
        }
             
    }
    
    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
    }
    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room does not exist");
        waitingText.SetText("The room does not exist");
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}

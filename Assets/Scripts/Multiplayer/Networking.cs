using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Networking : MonoBehaviourPunCallbacks
{
    [SerializeField] private GameObject findOpponentPanel = null;
    [SerializeField] private GameObject waitingStatusPanel = null;
    [SerializeField] private TextMeshProUGUI statusText = null;

    private bool isConnect = false;
    private string gameVersion = "1.0";
    private const int maxPlayers = 2;

    private void Awake()
    {
        PhotonNetwork.AutomaticallySyncScene = true;
    }
    public void findOpponent()
    {
        isConnect = true;
        findOpponentPanel.SetActive(false);
        waitingStatusPanel.SetActive(true);
        statusText.text = "Searching...";

        if (PhotonNetwork.IsConnected)
        {
            PhotonNetwork.JoinRandomRoom();
        }
        else
        {
            PhotonNetwork.GameVersion = gameVersion;
            PhotonNetwork.ConnectUsingSettings();
        }
    }
    public override void OnConnectedToMaster()
        {
        Debug.Log("Connected To Master");
            if(isConnect)
            {
            PhotonNetwork.JoinRandomRoom();
            }
        }

    public override void OnDisconnected(DisconnectCause cause)
        {
            waitingStatusPanel.SetActive(false);
            findOpponentPanel.SetActive(true);
        Debug.Log($"Disconnected due to: {cause}");

        }

    public override void OnJoinRandomFailed(short returnCode, string message)
        {
        Debug.Log("No client is waiting for opponent");
        PhotonNetwork.CreateRoom(null, new Photon.Realtime.RoomOptions { MaxPlayers = maxPlayers });
        }
    public override void OnJoinedRoom()
    {
        Debug.Log("Client successfully joined a room");
        int playerCount = PhotonNetwork.CurrentRoom.PlayerCount;
        if(playerCount != maxPlayers)
        {
            statusText.text = "Waiting for opponent";
            Debug.Log("Waiting for opponent");
        }
        else
        {
            statusText.text = "Opponent found";
            Debug.Log("Matching is ready to begin");
        }
    }

    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        if(PhotonNetwork.CurrentRoom.PlayerCount == maxPlayers)
        {
            PhotonNetwork.CurrentRoom.IsOpen = false;
            Debug.Log("Match is ready to begin");
            PhotonNetwork.LoadLevel("Level 1");
        }
    }
}



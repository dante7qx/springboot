import { RSocketClient, JsonSerializer, IdentitySerializer } from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

// backend ws endpoint
const wsURL = 'ws://localhost:6565/rsocket';

// rsocket client
const client = new RSocketClient({
    serializers: {
        data: JsonSerializer,
        metadata: IdentitySerializer
    },
    setup: {
        keepAlive: 60000,
        lifetime: 180000,
        dataMimeType: 'application/json',
        metadataMimeType: 'message/x.rsocket.routing.v0',
    },
    transport: new RSocketWebSocketClient({
        url: wsURL
    })
});

// error handler
const errorHanlder = (e) => console.log(e);
// response handler
const responseHanlder = (payload) => {
    const li = document.createElement('li');
    li.innerText = payload.data;
    li.classList.add('list-group-item', 'small')
    document.getElementById('result').appendChild(li);
}

// request to rsocket-websocket and response handling
const numberRequester = (socket, value) => {
    socket.requestStream({
        data: value,
        metadata: String.fromCharCode('number.stream'.length) + 'number.stream'
    }).subscribe({
        onError: errorHanlder,
        onNext: responseHanlder,
        onSubscribe: subscription => {
            subscription.request(100); // set it to some max value
        }
    })
}

// once the backend connection is established, register the event listeners
client.connect().then(socket => {
    document.getElementById('n').addEventListener('change', ({srcElement}) => {
        numberRequester(socket, parseInt(srcElement.value));
    })
}, errorHanlder);

/*
// reactive stream processor
const processor = new FlowableProcessor(sub => {});

const numberRequester = (socket, processor) => {
    socket.requestChannel(processor.map(i => {
        return {
            data: i,
            metadata: String.fromCharCode('number.channel'.length) + 'number.channel'
        }
    })).subscribe({
        onError: errorHanlder,
        onNext: responseHanlder,
        onSubscribe: subscription => {
            subscription.request(100); // set it to some max value
        }
    })
}

client.connect().then(sock => {
    numberRequester(sock, processor);
    document.getElementById('n').addEventListener('keyup', ({srcElement}) => {
        if(srcElement.value.length > 0){
            processor.onNext(parseInt(srcElement.value))
        }
    })
}, errorHanlder);
*/

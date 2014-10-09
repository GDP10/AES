package net.robinjam.aes;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.w3c.dom.Document;

public class NotificationProducer implements org.oasis_open.docs.wsn.bw_2.NotificationProducer {

	@Override
	public SubscribeResponse subscribe(Subscribe subscribeRequest)
			throws NotifyMessageNotSupportedFault,
			UnrecognizedPolicyRequestFault, TopicExpressionDialectUnknownFault,
			ResourceUnknownFault, InvalidTopicExpressionFault,
			UnsupportedPolicyRequestFault, InvalidFilterFault,
			InvalidProducerPropertiesExpressionFault,
			UnacceptableInitialTerminationTimeFault,
			SubscribeCreationFailedFault, TopicNotSupportedFault,
			InvalidMessageContentExpressionFault {
		System.out.println("Subscription registered");
		SubscribeResponse response = new SubscribeResponse();
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			System.exit(-1);
		}
		doc.appendChild(doc.createElement("test"));
		response.getAny().add(doc.getDocumentElement());
		return response;
	}

	@Override
	public GetCurrentMessageResponse getCurrentMessage(
			GetCurrentMessage getCurrentMessageRequest)
			throws InvalidTopicExpressionFault,
			TopicExpressionDialectUnknownFault, MultipleTopicsSpecifiedFault,
			ResourceUnknownFault, NoCurrentMessageOnTopicFault,
			TopicNotSupportedFault {
		System.out.println("Message requested");
		GetCurrentMessageResponse response = new GetCurrentMessageResponse();
		response.getAny().add("Hello universe!");
		return response;
	}

}

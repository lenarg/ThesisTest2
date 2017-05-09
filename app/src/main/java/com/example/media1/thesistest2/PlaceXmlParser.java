package com.example.media1.thesistest2;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlaceXmlParser {

    private static final String TAG = PlaceXmlParser.class.getSimpleName();
    private static final String PLACE_ID = "place_id";
    private static final String PLACE_NAME = "name";
    private static final String PLACE_DESCRIPTION = "description";
    private static final String PLACE_COORDINATES = "coordinates";

    public PlaceXmlParser() {
        super();
    }

    public List<PlaceOnMap> parsePlacesXml(final InputStream xmlStream) {
        PlaceOnMap place = null;
        final List<PlaceOnMap> placeList = new ArrayList<>();
        try {
            final XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            final XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setInput(xmlStream, null);
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {
                    final String name = parser.getName();
                    switch (name) {
                        case PLACE_ID:
                            place = new PlaceOnMap();
                            setPlaceId(parser, place);
                            break;
                        case PLACE_NAME:
                            setPlaceName(parser, place);
                            break;
                        case PLACE_DESCRIPTION:
                            setPlaceDescription(parser, place);
                            break;
                        case PLACE_COORDINATES:
                            setPlaceLatLong(parser, place);
                            placeList.add(place);
                            break;
                    }
                }
                event = parser.next();
            }
        } catch (final XmlPullParserException e) {
            Log.e(TAG, e.toString());
        } catch (final IOException e) {
            Log.e(TAG, e.toString());
        }
        return placeList;
    }

    private boolean areValidArgs(final XmlPullParser parser, final PlaceOnMap place) {
        return null != parser && null != place;
    }

    private void setPlaceId(final XmlPullParser parser, final PlaceOnMap place) {
        if (areValidArgs(parser, place)) {
            final String placeId = getTagValue(parser);
            place.setPlaceId(Integer.parseInt(placeId));
        }
    }

    private void setPlaceName(final XmlPullParser parser, final PlaceOnMap place) {
        if (areValidArgs(parser, place)) {
            final String placeName = getTagValue(parser);
            place.setPlaceName(placeName);
        }
    }

    private void setPlaceDescription(final XmlPullParser parser, final PlaceOnMap place) {
        if (areValidArgs(parser, place)) {
            final String placeDescription = getTagValue(parser);
            place.setPlaceDescription(placeDescription);
        }
    }

    private void setPlaceLatLong(final XmlPullParser parser, final PlaceOnMap place) {
        if (areValidArgs(parser, place)) {
            final String[] latLong = getTagValue(parser).split(";");
            if (3 == latLong.length) {
                place.setPlaceLatitude(Double.parseDouble(latLong[1]));
                place.setPlaceLongitude(Double.parseDouble(latLong[2]));
            }
        }
    }

    private String getTagValue(final XmlPullParser parser) {
        String result = "";
        try {
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
        } catch (final XmlPullParserException e) {
            Log.e(TAG, e.toString());
        } catch (final IOException e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }


}

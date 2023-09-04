import React from 'react'
import Admin from '../../admin';
import './index.less'
import Poem from '../../poem';
export default class Home extends React.Component {

    
    render() {
        return (
            <div>
                {/* <Admin /> */}
                <Poem />
            </div>
        );
    }
}